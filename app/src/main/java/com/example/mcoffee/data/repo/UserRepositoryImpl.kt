package com.example.mcoffee.data.repo

import android.util.Patterns
import com.example.mcoffee.data.model.user.Users
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.remote.user.AuthRequestState
import com.example.mcoffee.data.remote.user.UserFirebaseDataSource
import com.example.mcoffee.domain.repo.UserRepository
import com.google.firebase.FirebaseException
import kotlinx.coroutines.flow.Flow

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

    override fun getUserInfo(): Flow<Users> {
       return userFirebaseDataSource.getUserInfo()
    }

    override fun logout() {
        userFirebaseDataSource.logout()
    }

    override suspend fun updateUserProfile(
        user: Users?,
        updatedInformation: HashMap<String, Any?>
    ): FireBaseState<String> {
        return try {
            if (user != null) {
                userFirebaseDataSource.updateUserProfile(user, updatedInformation)
                FireBaseState.Success(null)
            } else {
                FireBaseState.Fail("")
            }
        } catch (ex: FirebaseException) {
            FireBaseState.Fail(ex.message)
        }
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