package com.example.mcoffee.domain.repo

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun addOrder(order: Order): FireBaseState<String>
    fun getAllOrders(): Flow<List<Order>>
    fun getAllOrdersByAdmin(): Flow<List<Order>>
    suspend fun confirmOrderByAdmin(order: Order): FireBaseState<String>
    suspend fun abortOrderByAdmin(order: Order): FireBaseState<String>
    suspend fun abortOrderByUser(order: Order): FireBaseState<String>
}