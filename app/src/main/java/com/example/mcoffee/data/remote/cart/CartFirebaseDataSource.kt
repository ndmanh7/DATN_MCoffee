package com.example.mcoffee.data.remote.cart

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.remote.FireBaseState

interface CartFirebaseDataSource {
    suspend fun addToCart(cart: Cart) :FireBaseState<String>
}