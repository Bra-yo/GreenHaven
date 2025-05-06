package com.brayo.greenhaven.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brayo.greenhaven.data.ProductDatabase
import com.brayo.greenhaven.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val context = app.applicationContext
    private val productDao = ProductDatabase.getDatabase(app).productDao()

    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    fun addProduct(
        name: String, 
        price: Double, 
        phone: String, 
        imageUri: String,
        ownerUsername: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedImagePath = saveImageToInternalStorage(Uri.parse(imageUri))
            val product = Product(
                id = UUID.randomUUID().toString(),
                name = name,
                price = price,
                imageUri = savedImagePath, // Use the saved local image path
                ownerUsername = ownerUsername, // Use the provided username
                phone = phone
            )
            productDao.insertProduct(product)
        }
    }

    fun updateProduct(updatedProduct: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.updateProduct(updatedProduct)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete image from storage
            product.imageUri?.let { deleteImageFromInternalStorage(it) }
            productDao.deleteProduct(product)
        }
    }

    fun getProductById(productId: String): Product? {
        return allProducts.value?.find { it.id == productId }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)

            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            "" // Return an empty string if saving fails
        }
    }

    private fun deleteImageFromInternalStorage(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}