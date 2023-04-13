package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.order.OrderFirebaseDataSource
import com.example.mcoffee.domain.repo.OrderRepository

class OrderRepositoryImpl(
    private val orderFirebaseDataSource: OrderFirebaseDataSource
) : OrderRepository {
    override suspend fun addOrder(order: Order): FireBaseState<String> {
        return orderFirebaseDataSource.addOrder(order)
    }
}