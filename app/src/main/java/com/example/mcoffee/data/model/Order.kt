package com.example.mcoffee.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Order(
    var uid: String = "",
    var userUid: String = "",
    var records: List<Record> = emptyList(),
    var orderDate: String = "",
    var totalPrice: Int = 0,
    var status: Boolean = false
) {
}