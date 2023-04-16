package com.example.mcoffee.data.remote.user

import com.example.mcoffee.data.model.user.Users
import kotlinx.coroutines.flow.Flow

interface UserFirebaseDataSource {
    suspend fun login(email: String, password: String): AuthRequestState
    suspend fun register(email: String, password: String): AuthRequestState
    fun getUserInfo(): Flow<Users>
    fun logout()
    suspend fun updateUserProfile(users: Users?, updatedInformation: HashMap<String, Any?>): Boolean

}