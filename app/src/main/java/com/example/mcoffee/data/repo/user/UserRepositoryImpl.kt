package com.example.mcoffee.data.repo.user

import android.util.Patterns
import com.example.mcoffee.data.remote.user.AuthRequestState
import com.example.mcoffee.data.remote.user.UserFirebaseDataSource

class UserRepositoryImpl(
    private val userFirebaseDataSource: UserFirebaseDataSource
): UserRepository {
    override suspend fun login(email: String, password: String): AuthRequestState {
        return if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                userFirebaseDataSource.login(email, password)
            } else {
                AuthRequestState.Fail("Mật khẩu không được để trống")
            }
        } else {
            AuthRequestState.Fail("Email không được để trống")
        }
    }

    override suspend fun register(email: String, password: String): AuthRequestState {
        return validateInputFields(email, password)
    }

    private suspend fun validateInputFields(email: String, password: String): AuthRequestState {
        return if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    userFirebaseDataSource.register(email, password)
                } else {
                    AuthRequestState.Fail("Email không hợp lệ")
                }
            } else {
                AuthRequestState.Fail("Mật khẩu không được để trống")
            }
        } else {
            AuthRequestState.Fail("Email không được để trống")
        }
    }
}