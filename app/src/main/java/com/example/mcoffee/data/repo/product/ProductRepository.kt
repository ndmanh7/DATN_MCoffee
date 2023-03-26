package com.example.mcoffee.data.repo.product

import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>>
}