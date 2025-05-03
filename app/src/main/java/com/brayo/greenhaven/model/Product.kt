package com.brayo.greenhaven.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val phone: String,
    val imagePath: String,
    val description : String
) {
    // Computed property to map imagePath to imageUri
    val imageUri: String
        get() = imagePath // Use imagePath as the URI
}
