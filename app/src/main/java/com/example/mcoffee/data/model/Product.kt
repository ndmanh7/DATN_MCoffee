package com.example.mcoffee.data.model

import com.example.mcoffee.data.model.category.Category
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product(
    var uid: String = "",
    var categoryUid: String = "",
    var productName: String = "",
    var description: String = "",
    var price: Int = 0,
    var size: String = "",
    var image: String = ""
) : java.io.Serializable
