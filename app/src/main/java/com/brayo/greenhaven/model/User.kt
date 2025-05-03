package com.brayo.greenhaven.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val role: String, // "admin" or "user"
    val profileImageUri: String? = null // New field for profile image URI
)
