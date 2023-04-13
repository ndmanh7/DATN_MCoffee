package com.example.mcoffee.data.remote.order

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState

interface OrderFirebaseDataSource {
    suspend fun addOrder(order: Order): FireBaseState<String>
}