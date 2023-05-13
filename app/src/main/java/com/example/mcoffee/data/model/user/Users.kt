package com.example.mcoffee.data.model.user

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    var uid: String = "",
    var email: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var role: String = "",
    var image: String = ""
)