package com.example.mcoffee.data.remote.product

import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import kotlinx.coroutines.flow.Flow

interface ProductFirebaseDataSource {
    suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>>
    fun searchProduct(searchString: String): Flow<ArrayList<Product>>
    suspend fun addProductByAdmin(product: Product): Boolean
    suspend fun editProductByAdmin(product: Product, updatedInformation: HashMap<String, Any?>): Boolean
    suspend fun removeProductByAdmin(product: Product): Boolean
}