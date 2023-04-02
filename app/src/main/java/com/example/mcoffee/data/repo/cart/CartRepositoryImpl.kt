package com.example.mcoffee.data.repo.cart

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.cart.CartFirebaseDataSource

class CartRepositoryImpl(
    private val cartFirebaseDataSource: CartFirebaseDataSource
) : CartRepository {
    override suspend fun addToCart(cart: Cart): FireBaseState<String> {
        return cartFirebaseDataSource.addToCart(cart)
    }

}