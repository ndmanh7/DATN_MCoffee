package com.example.mcoffee.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Record(
    var uid: String = "",
    var product: Product? = null,
    var amount: Int = 0,
    var totalPrice: Int = 0
) : java.io.Serializable