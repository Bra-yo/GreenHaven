package com.brayo.greenhaven.repository

import com.brayo.greenhaven.data.UserDao
import com.brayo.greenhaven.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}