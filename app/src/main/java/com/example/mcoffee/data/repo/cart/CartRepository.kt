package com.example.mcoffee.data.repo.cart

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.remote.FireBaseState

interface CartRepository {
    suspend fun addToCart(cart: Cart): FireBaseState<String>
}