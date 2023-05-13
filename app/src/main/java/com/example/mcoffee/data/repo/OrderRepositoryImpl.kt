package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.order.OrderFirebaseDataSource
import com.example.mcoffee.domain.repo.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val orderFirebaseDataSource: OrderFirebaseDataSource
) : OrderRepository {
    override suspend fun addOrder(order: Order): FireBaseState<String> {
        return orderFirebaseDataSource.addOrder(order)
    }

    override fun getAllOrders(): Flow<List<Order>> {
        return orderFirebaseDataSource.getAllOrders()
    }

    override fun getAllOrdersByAdmin(): Flow<List<Order>> {
        return orderFirebaseDataSource.getAllOrdersByAdmin()
    }

    override suspend fun confirmOrderByAdmin(order: Order): FireBaseState<String> {
        return orderFirebaseDataSource.confirmOrdersByAdmin(order)
    }

    override suspend fun abortOrderByAdmin(order: Order): FireBaseState<String> {
        return orderFirebaseDataSource.abortOrdersByAdmin(order)
    }

    override suspend fun abortOrderByUser(order: Order): FireBaseState<String> {
        return orderFirebaseDataSource.abortOrdersByUser(order)
    }
}