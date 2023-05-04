package com.example.mcoffee.data.remote.category

import com.example.mcoffee.data.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryFirebaseDataSource {
    suspend fun getAllCategories(): Flow<ArrayList<Category>>

    suspend fun addCategoryByAdmin(category: Category): Boolean
    suspend fun editCategoryByAdmin(category: Category, newInformation: HashMap<String, Any?>): Boolean
    suspend fun deleteCategoryByAdmin(category: Category): Boolean
}