package com.example.mcoffee.data.remote.cart

import android.util.Log
import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
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

    override suspend fun addToCart(product: Product): FireBaseState<String> {
        return try {
            val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid).child(product.uid)
            val result = cartRef.setValue(product).await()
            FireBaseState.Success("")
        } catch (ex: FirebaseException) {
            FireBaseState.Fail(ex.toString())
        }
    }

    override suspend fun getProductsInCart(): Flow<ArrayList<Product>> {
        val productList = arrayListOf<Product>()
        val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid)

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
                    for (prod in snapshot.children) {
                        val product = prod.getValue(Product::class.java)
                        product?.let {
                            productList.add(it)
                        }
                    }
                    trySend(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            cartRef.addValueEventListener(valueEventListener)
            awaitClose { cartRef.removeEventListener(valueEventListener) }
        }
    }

    override fun removeFromCart(product: Product) {
        val cartRef = databaseReference.child("Cart").child(auth.currentUser!!.uid).child(product.uid).removeValue()
    }

}