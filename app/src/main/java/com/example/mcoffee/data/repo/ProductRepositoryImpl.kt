package com.example.mcoffee.data.repo

import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSource
import com.example.mcoffee.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productFirebaseDataSource: ProductFirebaseDataSource
) : ProductRepository {
    override suspend fun getProductsByCategory(category: Category): Flow<ArrayList<Product>> {
        return productFirebaseDataSource.getProductsByCategory(category)
    }

    override fun searchProduct(searchString: String): Flow<List<Product>> {
        return productFirebaseDataSource.searchProduct(searchString)
    }

    override suspend fun addProductByAdmin(product: Product): Boolean {
        return productFirebaseDataSource.addProductByAdmin(product)
    }

    override suspend fun editProductByAdmin(
        product: Product,
        updatedInformation: HashMap<String, Any?>
    ): Boolean {
        return productFirebaseDataSource.editProductByAdmin(product, updatedInformation)
    }

    override suspend fun removeProductByAdmin(product: Product): Boolean {
        return productFirebaseDataSource.removeProductByAdmin(product)
    }

}