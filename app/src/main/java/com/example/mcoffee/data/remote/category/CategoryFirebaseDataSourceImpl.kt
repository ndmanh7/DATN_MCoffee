package com.example.mcoffee.data.remote.category

import android.util.Log
import com.example.mcoffee.data.model.category.Category
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

class CategoryFirebaseDataSourceImpl(
    private val databaseReference: DatabaseReference
) : CategoryFirebaseDataSource {

    override suspend fun getAllCategories(): Flow<ArrayList<Category>> {
            val categoryList = arrayListOf<Category>()
            val categoryRef = databaseReference.child("Category")
        Log.d("manh", "getAllCategories at line 23: $categoryRef")

         return callbackFlow {
             val valueEventListener = object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     categoryList.clear()
                     for (cat in snapshot.children) {
                         val category = cat.getValue(Category::class.java)
                         val products = cat.child("product")
                         Log.d("manh", "products at line 32: ${products.value}")
                         Log.d("manh", "onDataChange: ${cat.getValue(Category::class.java)}")
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

}