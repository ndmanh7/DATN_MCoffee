package com.example.mcoffee.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Order(
    var uid: String = "",
    var userUid: String = "",
    var receiverName: String = "",
    var receiverPhone: String = "",
    var receiverAddress: String = "",
    var records: List<Record> = emptyList(),
    var orderDate: String = "",
    var totalPrice: Int = 0,
    var isConfirmed: Boolean = false
) {
}