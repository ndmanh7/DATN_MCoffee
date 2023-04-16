package com.example.mcoffee.domain.repo

import com.example.mcoffee.data.model.user.Users
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.user.AuthRequestState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
    fun getUserInfo(): Flow<Users>
    fun logout()
    suspend fun updateUserProfile(user: Users?, updatedInformation: HashMap<String, Any?>): FireBaseState<String>

}