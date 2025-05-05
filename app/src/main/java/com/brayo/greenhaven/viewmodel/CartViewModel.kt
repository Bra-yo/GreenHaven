package com.brayo.greenhaven.viewmodel

import androidx.lifecycle.ViewModel
import com.brayo.greenhaven.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    fun addToCart(item: CartItem) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.productId == item.productId }
        
        if (existingItem != null) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(item)
        }
        
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.productId != productId }
        updateTotalPrice()
    }

    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }

        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.productId == productId }
        if (index != -1) {
            currentItems[index] = currentItems[index].copy(quantity = quantity)
            _cartItems.value = currentItems
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price * it.quantity }
    }
}
