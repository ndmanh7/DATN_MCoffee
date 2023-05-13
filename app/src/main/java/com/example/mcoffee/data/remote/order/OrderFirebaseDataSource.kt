package com.example.mcoffee.data.remote.order

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import kotlinx.coroutines.flow.Flow

interface OrderFirebaseDataSource {
    suspend fun addOrder(order: Order): FireBaseState<String>
    fun getAllOrders(): Flow<ArrayList<Order>>
    fun getAllOrdersByAdmin(): Flow<ArrayList<Order>>
    suspend fun confirmOrdersByAdmin(order: Order): FireBaseState<String>
    suspend fun abortOrdersByAdmin(order: Order): FireBaseState<String>
    suspend fun abortOrdersByUser(order: Order): FireBaseState<String>

}