package com.example.mcoffee.di

import com.example.mcoffee.data.remote.user.UserFirebaseDataSource
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSource
import com.example.mcoffee.data.repo.*
import com.example.mcoffee.data.repo.category.CategoryRepository
import com.example.mcoffee.data.repo.category.CategoryRepositoryImpl
import com.example.mcoffee.data.repo.product.ProductRepository
import com.example.mcoffee.data.repo.product.ProductRepositoryImpl
import com.example.mcoffee.data.repo.user.UserRepository
import com.example.mcoffee.data.repo.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userFirebaseDataSource: UserFirebaseDataSource): UserRepository {
        return UserRepositoryImpl(userFirebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryFirebaseDataSource: CategoryFirebaseDataSource): CategoryRepository {
        return CategoryRepositoryImpl(categoryFirebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productFirebaseDataSource: ProductFirebaseDataSource): ProductRepository {
        return ProductRepositoryImpl(productFirebaseDataSource)
    }
}