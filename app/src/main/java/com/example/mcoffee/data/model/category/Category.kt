package com.example.mcoffee.data.model.category

import com.example.mcoffee.data.model.Product
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Category(
    var uid: String = "",
    var categoryName: String = "",
    var products: List<Product>? = null
)
