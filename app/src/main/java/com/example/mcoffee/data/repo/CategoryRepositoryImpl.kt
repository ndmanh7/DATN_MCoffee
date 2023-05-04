package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.example.mcoffee.domain.repo.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryFirebaseDataSource: CategoryFirebaseDataSource
) : CategoryRepository {

    override suspend fun getAllCategories(): Flow<ArrayList<Category>> {
        return categoryFirebaseDataSource.getAllCategories()
    }

    override suspend fun addCategoryByAdmin(category: Category): Boolean {
        return categoryFirebaseDataSource.addCategoryByAdmin(category)
    }

    override suspend fun editCategoryByAdmin(
        category: Category,
        newInformation: HashMap<String, Any?>
    ): Boolean {
        return categoryFirebaseDataSource.editCategoryByAdmin(category, newInformation)
    }

    override suspend fun deleteCategoryByAdmin(category: Category): Boolean {
        return categoryFirebaseDataSource.deleteCategoryByAdmin(category)
    }

}