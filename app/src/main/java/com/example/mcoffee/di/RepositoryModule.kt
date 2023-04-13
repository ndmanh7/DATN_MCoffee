package com.example.mcoffee.di

import com.example.mcoffee.data.remote.cart.CartFirebaseDataSource
import com.example.mcoffee.data.remote.user.UserFirebaseDataSource
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.example.mcoffee.data.remote.order.OrderFirebaseDataSource
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSource
import com.example.mcoffee.data.repo.*
import com.example.mcoffee.domain.repo.CartRepository
import com.example.mcoffee.data.repo.CartRepositoryImpl
import com.example.mcoffee.domain.repo.CategoryRepository
import com.example.mcoffee.data.repo.CategoryRepositoryImpl
import com.example.mcoffee.domain.repo.OrderRepository
import com.example.mcoffee.data.repo.OrderRepositoryImpl
import com.example.mcoffee.domain.repo.ProductRepository
import com.example.mcoffee.data.repo.ProductRepositoryImpl
import com.example.mcoffee.domain.repo.UserRepository
import com.example.mcoffee.data.repo.UserRepositoryImpl
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

    @Provides
    @Singleton
    fun provideCartRepository(cartFirebaseDataSource: CartFirebaseDataSource): CartRepository {
        return CartRepositoryImpl(cartFirebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderFirebaseDataSource: OrderFirebaseDataSource): OrderRepository {
        return OrderRepositoryImpl(orderFirebaseDataSource)
    }
}