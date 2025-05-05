package com.brayo.greenhaven.ui.screens.products

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.brayo.greenhaven.R
import com.brayo.greenhaven.model.Product
import com.brayo.greenhaven.ui.screens.userproducts.BottomNavigationBar
import com.brayo.greenhaven.ui.theme.green
import com.brayo.greenhaven.viewmodel.ProductViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProductScreen(navController: NavController, viewModel: ProductViewModel) {
    val productList by viewModel.allProducts.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredProducts = remember(searchQuery, productList) {
        productList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Products", fontSize = 20.sp) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(Color.LightGray)
                )

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    placeholder = { Text("Search products...") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray
                    )
                )
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = green)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(filteredProducts.size) { index ->
                    ProductItem(navController, filteredProducts[index], viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ProductItem(navController: NavController, product: Product, viewModel: ProductViewModel) {
    val painter: Painter = if (product.imageUri.isNullOrEmpty()) {
        painterResource(id = R.drawable.loading) // Replace with your placeholder image
    } else {
        rememberAsyncImagePainter(model = Uri.parse(product.imageUri))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Product Image
            Image(
                painter = painter,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        navController.navigate("product_details/${product.id}")
                    },
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
            )

            // Product Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 60.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Price: Ksh${product.price}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Add to Cart Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
            ) {
                OutlinedButton(
                    onClick = {
                       // viewModel.addToCart(product) // Add product to cart
                       // navController.navigate("cart_screen") // Navigate to the cart screen
                    },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to Cart"
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Add to Cart")
                    }
                }
            }
        }
    }
}