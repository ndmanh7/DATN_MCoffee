package com.example.mcoffee.data.remote.user

interface UserFirebaseDataSource {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
}