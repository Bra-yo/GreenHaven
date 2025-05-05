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
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
private var isLoading: Boolean = false

    fun registerUser(user: User, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.registerUser(user)
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred during registration")
            }
        }
    }

    fun loginUser(email: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.loginUser(email, password)
                _currentUser.value = (user) // Update LiveData
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred during login")
            }
        }
    }

    fun logoutUser() {
        _currentUser.asStateFlow() // Clear the current user on logout
    }

    fun isLoggedIn(): Boolean {
        return _currentUser.value != null
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