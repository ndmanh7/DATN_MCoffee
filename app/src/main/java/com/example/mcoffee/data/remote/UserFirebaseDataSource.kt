package com.example.mcoffee.data.remote

interface UserFirebaseDataSource {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
}