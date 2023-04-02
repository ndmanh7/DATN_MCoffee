package com.example.mcoffee.data.remote

sealed class FireBaseState<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Success<T>(data: T?) : FireBaseState<T>(data, null)
    class Fail<T>(msg: String?) : FireBaseState<T>(null, msg)
    class Loading()
}