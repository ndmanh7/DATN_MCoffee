package com.example.mcoffee.data.remote.cart

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.remote.FireBaseState
import kotlinx.coroutines.flow.Flow

interface CartFirebaseDataSource {
    suspend fun addToCart(product: Product): FireBaseState<String>

    suspend fun getProductsInCart(): Flow<ArrayList<Product>>

    fun removeFromCart(product: Product)
}