package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.cart.CartFirebaseDataSource
import com.example.mcoffee.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val cartFirebaseDataSource: CartFirebaseDataSource
) : CartRepository {
    override suspend fun addToCart(product: Product): FireBaseState<String> {
        return cartFirebaseDataSource.addToCart(product)
    }

    override suspend fun getProductsInCart(): Flow<ArrayList<Product>> {
        return cartFirebaseDataSource.getProductsInCart()
    }

    override fun removeFromCart(product: Product) {
        cartFirebaseDataSource.removeFromCart(product)
    }

}