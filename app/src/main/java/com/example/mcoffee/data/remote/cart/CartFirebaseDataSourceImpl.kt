package com.example.mcoffee.data.remote.cart

import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.remote.FireBaseState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class CartFirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val databaseReference: DatabaseReference
) : CartFirebaseDataSource {

    override suspend fun addToCart(cart: Cart): FireBaseState<String> {
        return try {
            val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid).push()
            cart.apply {
                uid = cartRef.key.toString()
                userUid = auth.currentUser!!.uid
            }
            val result = cartRef.setValue(cart).await()
            FireBaseState.Success("")
        } catch (ex: FirebaseException) {
            FireBaseState.Fail(ex.toString())
        }
    }

}