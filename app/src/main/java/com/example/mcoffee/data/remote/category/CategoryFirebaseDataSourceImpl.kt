package com.example.mcoffee.data.remote.category

import android.util.Log
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class CategoryFirebaseDataSourceImpl(
    private val databaseReference: DatabaseReference
) : CategoryFirebaseDataSource {

    override suspend fun getAllCategories(): Flow<ArrayList<Category>> {
            val categoryList = arrayListOf<Category>()
            val categoryRef = databaseReference.child("Category")

         return callbackFlow {
             val valueEventListener = object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     categoryList.clear()
                     for (cat in snapshot.children) {
                         val category = cat.getValue(Category::class.java)
                         if (category != null) {
                             categoryList.add(category)
                         }
                     }
                     trySend(categoryList)
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }
             }
             categoryRef.addValueEventListener(valueEventListener)
             awaitClose { categoryRef.removeEventListener(valueEventListener) }
         }

    }

    override suspend fun addCategoryByAdmin(category: Category): Boolean {
        return try {
            val categoryRef = databaseReference.child("Category").push()
            category.uid = categoryRef.key.toString()
            categoryRef.setValue(category).await()
            true
        } catch (ex: FirebaseException) {
            false
        }
    }

    override suspend fun editCategoryByAdmin(
        category: Category,
        newInformation: HashMap<String, Any?>
    ): Boolean {
        return try {
            val categoryRef = databaseReference.child("Category").child(category.uid)
            categoryRef.updateChildren(newInformation).await()
            true
        } catch (ex: FirebaseException) {
            false
        }
    }

    override suspend fun deleteCategoryByAdmin(category: Category): Boolean {
        return try {
            val categoryRef = databaseReference.child("Category").child(category.uid)
            val productRef = databaseReference.child("Product")
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (prod in snapshot.children) {
                        val product = prod.getValue(Product::class.java)
                        if (product?.categoryUid == category.uid) {
                            productRef.child(product.uid).removeValue()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            categoryRef.removeValue()
            productRef.addValueEventListener(valueEventListener)
            true
        } catch (ex: FirebaseException) {
            false
        }

    }

}