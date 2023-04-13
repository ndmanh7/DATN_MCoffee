package com.example.mcoffee.domain.repo

import com.example.mcoffee.data.remote.user.AuthRequestState

interface UserRepository {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
}