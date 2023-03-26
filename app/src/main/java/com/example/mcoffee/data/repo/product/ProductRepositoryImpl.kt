package com.example.mcoffee.data.repo.product

import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSource
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productFirebaseDataSource: ProductFirebaseDataSource
) : ProductRepository {
    override suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>> {
        return productFirebaseDataSource.getProductsByCategory(category)
    }
}