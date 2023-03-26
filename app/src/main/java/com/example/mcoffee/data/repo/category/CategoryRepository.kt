package com.example.mcoffee.data.repo.category

import com.example.mcoffee.data.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): Flow<ArrayList<Category>>
}