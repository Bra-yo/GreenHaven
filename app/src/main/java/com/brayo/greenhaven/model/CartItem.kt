package com.brayo.greenhaven.model

data class CartItem(
    val id: String,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String? = null
)