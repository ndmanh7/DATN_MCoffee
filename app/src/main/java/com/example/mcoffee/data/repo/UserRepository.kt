package com.example.mcoffee.data.repo

import com.example.mcoffee.data.remote.AuthRequestState

interface UserRepository {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
}