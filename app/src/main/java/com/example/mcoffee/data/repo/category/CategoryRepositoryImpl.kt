package com.example.mcoffee.data.repo.category

import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryFirebaseDataSource: CategoryFirebaseDataSource
) : CategoryRepository {

    override suspend fun getAllCategories(): Flow<ArrayList<Category>> {
        return categoryFirebaseDataSource.getAllCategories()
    }

}