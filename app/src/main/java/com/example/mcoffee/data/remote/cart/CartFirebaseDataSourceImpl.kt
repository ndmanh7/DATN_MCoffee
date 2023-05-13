package com.example.mcoffee.data.remote.cart

import android.util.Log
import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.data.remote.FireBaseState
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

class CartFirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val databaseReference: DatabaseReference
) : CartFirebaseDataSource {

    override suspend fun addToCart(record: Record): FireBaseState<String> {
        return try {
            val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid).push()
            record.uid = cartRef.key.toString()
            val result = cartRef.setValue(record).await()
            FireBaseState.Success("")
        } catch (ex: FirebaseException) {
            FireBaseState.Fail(ex.toString())
        }
    }

    override suspend fun getProductsInCart(): Flow<ArrayList<Record>> {
        val recordList = arrayListOf<Record>()
        val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid)

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    recordList.clear()
                    for (rec in snapshot.children) {
                        val record = rec.getValue(Record::class.java)
                        record?.let {
                            recordList.add(it)
                        }
                    }
                    trySend(recordList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            cartRef.addValueEventListener(valueEventListener)
            awaitClose { cartRef.removeEventListener(valueEventListener) }
        }
    }

    override fun removeFromCart(recordList: List<Record>): FireBaseState<String> {
        return try {
            val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid)
            for (records in recordList) {
                cartRef.child(records.uid).removeValue()
            }
            FireBaseState.Success("")
        } catch (ex: FirebaseException) {
            FireBaseState.Fail("Có lỗi xảy ra")
        }
    }

}