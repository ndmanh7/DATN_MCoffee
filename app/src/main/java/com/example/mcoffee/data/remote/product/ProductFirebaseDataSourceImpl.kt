package com.example.mcoffee.data.remote.product

import android.util.Log
import androidx.core.net.toUri
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
class ProductFirebaseDataSourceImpl(
    private val databaseReference: DatabaseReference,
    private val storageReference: StorageReference
) : ProductFirebaseDataSource {

    override suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>> {
        val productList = arrayListOf<Product>()
//        val categoryRef = databaseReference.child("Category")
        val productRef = databaseReference.child("Product")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
//                    for (cat in snapshot.children) {
//                        val cate = cat.getValue(Category::class.java)
//                        cate?.let {
//                            if (it == category) {
//                                for (prod in cat.child("product").children) {
//                                    val product = prod.getValue(Product::class.java)
//                                    product?.let { product1 ->
//                                        productList.add(product1)
//                                    }
//                                }
//                            }
//                        }
//                    }

                    for (prod in snapshot.children) {
                        val product = prod.getValue(Product::class.java)
                        product?.let {
                            if (it.categoryUid == category.uid) {
                                productList.add(it)
                            }
                        }
                    }
                    trySend(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            productRef.addValueEventListener(valueEventListener)
            awaitClose{productRef.removeEventListener(valueEventListener)}
        }
    }

    override fun searchProduct(searchString: String): Flow<ArrayList<Product>> {
        val productList = arrayListOf<Product>()
        val productRef = databaseReference.child("Product")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
                    for (prod in snapshot.children) {
                        val product = prod.getValue(Product::class.java)
                        product?.let {
                           if (it.productName.contains(searchString, true)) {
                               productList.add(it)
                           }
                        }
                    }
                    trySend(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            productRef.addValueEventListener(valueEventListener)
            awaitClose { productRef.removeEventListener(valueEventListener) }
        }
    }

    override suspend fun addProductByAdmin(product: Product): Boolean {
        return try {
            val productRef = databaseReference.child("Product").push()
            product.uid = productRef.key.toString()
            val storageRef = storageReference.child(product.uid)
            storageRef.putFile(product.image.toUri()).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    product.image = downloadUri.toString()
                }
            }.await()
            productRef.setValue(product).await()
            true
        } catch (ex: FirebaseException) {
            false
        }

    }

    override suspend fun editProductByAdmin(
        product: Product,
        updatedInformation: HashMap<String, Any?>
    ): Boolean {
        return try {
            if (product != null) {
                val productRef = databaseReference.child("Product").child(product.uid)
                val storageRef = storageReference.child(product.uid)
                if (product.image == null) {
                    storageRef.putFile(product.image.toUri()).continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        storageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            updatedInformation["/image"] = downloadUri.toString()
                        }
                    }.addOnFailureListener {
                        updatedInformation["/image"] = product.image
                    }
                        .await()
                }
                productRef.updateChildren(updatedInformation)
            }
            true
        } catch (ex: FirebaseException) {
            false
        }
    }

    override suspend fun removeProductByAdmin(product: Product): Boolean {
        return try {
            val productRef = databaseReference.child("Product").child(product.uid)
            productRef.removeValue().await()
            true
        } catch (ex: FirebaseException) {
            false
        }
    }
}