package com.example.mcoffee.data.remote.user

import android.util.Log
import com.example.mcoffee.data.model.user.Users
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class UserFirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val databaseRef: DatabaseReference
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
            val currentUser = auth.currentUser
            currentUser?.let {
                databaseRef.child("User")
                    .child(it.uid)
                    .setValue(
                        Users().apply {
                            uid = it.uid
                            this.email = email
                            this.password = password
                        }
                    )
            }
            AuthRequestState.Success("Đăng ký thành công")
        } catch (ex: FirebaseAuthException) {
            AuthRequestState.Fail("$ex")
        }
    }

    override fun getUserInfo(): Flow<Users> {
        val userRef = databaseRef.child("User").child(auth.currentUser!!.uid)
        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(Users::class.java)
                    user?.let {
                        Log.d("manh", "onDataChange at line 59: $it")
                        trySend(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }

            userRef.addValueEventListener(valueEventListener)
            awaitClose { userRef.removeEventListener(valueEventListener) }
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun updateUserProfile(
        users: Users?,
        updatedInformation: HashMap<String, Any?>
    ): Boolean {
        return try {
            if (users != null) {
                users.uid = auth.currentUser!!.uid
                val userRef = databaseRef.child("User").child(users.uid)
                userRef.updateChildren(updatedInformation)
                true
            } else {
                false
            }
        } catch (ex: FirebaseException) {
            false
        }
    }


}