package com.example.mcoffee.data.remote.order

import android.util.Log
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun getAllOrders(): Flow<ArrayList<Order>> {
        val orderList = arrayListOf<Order>()
        val orderRef = databaseReference.child("Order").child(auth.currentUser!!.uid)
        return callbackFlow {
            val orderValueListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   orderList.clear()
                    for (item in snapshot.children) {
                        val order = item.getValue(Order::class.java)
                        order?.let {
                            orderList.add(it)
                        }
                    }
                    trySend(orderList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            orderRef.addValueEventListener(orderValueListener)
            awaitClose { orderRef.removeEventListener(orderValueListener) }
        }
    }

    override fun getAllOrdersByAdmin(): Flow<ArrayList<Order>> {
        val orderList = arrayListOf<Order>()
        val orderRef = databaseReference.child("Order")
        return callbackFlow {
            val orderValueListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    orderList.clear()
                    for (item in snapshot.children) {
                        val order = item.getValue(Order::class.java)
                        order?.let {
                            for (ord in item.child(it.userUid).children) {
                                val res = ord.getValue(Order::class.java)
                                res?.let {  result ->
                                    orderList.add(result)
                                }
                            }
                        }
                    }
                    Log.d("manh", "onDataChange at line 77: $orderList")
                    trySend(orderList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            orderRef.addValueEventListener(orderValueListener)
            awaitClose { orderRef.removeEventListener(orderValueListener) }
        }
    }
}