package com.brayo.greenhaven.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayo.greenhaven.model.User
import com.brayo.greenhaven.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    var loggedInUser: ((User?) -> Unit)? = null

    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    private var isLoading: Boolean = false

    fun registerUser(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            loggedInUser?.invoke(user)
        }
    }

    fun logoutUser() {
        _currentUser.asStateFlow() // Clear the current user on logout
    }

    fun isLoggedIn(): Boolean {
        return _currentUser.value != null
    }

    fun checkCurrentUser() {
        viewModelScope.launch {
            try {
                isLoading = true
                val user = repository.getCurrentUser()
                _currentUser.value = user
                loggedInUser?.invoke(user)
            } catch (e: Exception) {
                // Handle any errors
                _currentUser.value = null
                loggedInUser?.invoke(null)
            } finally {
                isLoading = false
            }
        }
    }

    fun refreshUserData() {
        viewModelScope.launch {
            try {
                isLoading = true
                // Fetch updated user data from your data source
                // For example:
                // val updatedUser = userRepository.getCurrentUser()
                // _currentUser.value = updatedUser
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
}