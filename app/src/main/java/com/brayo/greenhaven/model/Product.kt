package com.brayo.greenhaven.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String ,
    val name: String ,
    val price: Double ,
    val imageUri: String?, // Local image path
    val ownerUsername: String, // Username of the product owner
    val phone: String // Phone number of the owner
)
