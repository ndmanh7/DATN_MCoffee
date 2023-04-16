package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.cart.CartFirebaseDataSource
import com.example.mcoffee.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val cartFirebaseDataSource: CartFirebaseDataSource
) : CartRepository {
    override suspend fun addToCart(record: Record): FireBaseState<String> {
        return cartFirebaseDataSource.addToCart(record)
    }

    override suspend fun getProductsInCart(): Flow<ArrayList<Record>> {
        return cartFirebaseDataSource.getProductsInCart()
    }

    override fun removeFromCart(record: Record) {
        cartFirebaseDataSource.removeFromCart(record)
    }

}