package com.example.mcoffee.domain.repo

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.data.remote.FireBaseState
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(record: Record): FireBaseState<String>
    suspend fun getProductsInCart(): Flow<ArrayList<Record>>
    fun removeFromCart(recordList: List<Record>): FireBaseState<String>
}