package com.example.mcoffee.data.remote.product

import android.util.Log
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Suppress("UNCHECKED_CAST")
class ProductFirebaseDataSourceImpl(
    private val databaseReference: DatabaseReference
) : ProductFirebaseDataSource {

    override suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>> {
        val productList = arrayListOf<Product>()
        val categoryRef = databaseReference.child("Category")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
                    for (cat in snapshot.children) {
                        val cate = cat.getValue(Category::class.java)
                        cate?.let {
                            if (it == category) {
                                for (prod in cat.child("product").children) {
                                    val product = prod.getValue(Product::class.java)
                                    product?.let { product1 ->
                                        productList.add(product1)
                                    }
                                }
                            }
                        }
                    }
                    Log.d("manh", "productList at line 36: $productList")
                    trySend(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            categoryRef.addValueEventListener(valueEventListener)
            awaitClose{categoryRef.removeEventListener(valueEventListener)}
        }
    }

}