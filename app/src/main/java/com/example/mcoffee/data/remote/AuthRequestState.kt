package com.example.mcoffee.data.remote

sealed class AuthRequestState {
    data class Success(val msg: String) : AuthRequestState()
    data class Fail(val msg: String) : AuthRequestState()
}