package com.example.mcoffee.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Cart(
    var uid: String = "",
    var userUid: String = "",
    var productName: String = "",
    var orderDate: String = "",
    var orderAmount: Int = 0,
    var price: Int = 0,
    var totalPrice: Int = 0
)