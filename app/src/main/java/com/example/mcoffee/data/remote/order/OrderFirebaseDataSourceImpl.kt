package com.example.mcoffee.data.remote.order

import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class OrderFirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val databaseReference: DatabaseReference
) : OrderFirebaseDataSource {
    override suspend fun addOrder(order: Order): FireBaseState<String> {
        return try {
            val orderRef = databaseReference.child("Order").child(auth.currentUser!!.uid).push()
            val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid)
            order.apply {
                userUid = auth.currentUser!!.uid
                uid = orderRef.key.toString()
                status = false
            }
            orderRef.setValue(order).await()
            for (records in order.records) {
                cartRef.child(records.uid).removeValue()
            }
            FireBaseState.Success("")
        } catch (ex: FirebaseException) {
            FireBaseState.Fail(ex.toString())
        }
    }
}