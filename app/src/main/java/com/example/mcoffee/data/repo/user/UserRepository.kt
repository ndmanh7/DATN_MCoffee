package com.example.mcoffee.data.repo.user

import com.example.mcoffee.data.remote.user.AuthRequestState

interface UserRepository {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
}