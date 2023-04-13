package com.example.mcoffee.data.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await


class UserFirebaseDataSourceImpl(
    private val auth: FirebaseAuth
) : UserFirebaseDataSource {


    override suspend fun login(email: String, password: String): AuthRequestState {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            AuthRequestState.Success("Đăng nhập thành công")
        } catch (ex: FirebaseAuthException) {
            AuthRequestState.Fail("Tài khoản hoặc mật khẩu không chính xác")
        }
    }

    override suspend fun register(email: String, password: String): AuthRequestState {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            AuthRequestState.Success("Đăng ký thành công")
        } catch (ex: FirebaseAuthException) {
            AuthRequestState.Fail("$ex")
        }
    }


}