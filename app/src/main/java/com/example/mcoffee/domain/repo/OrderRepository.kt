package com.example.mcoffee.domain.repo

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState

interface OrderRepository {
    suspend fun addOrder(order: Order): FireBaseState<String>
}