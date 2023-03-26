package com.example.mcoffee.di

import com.example.mcoffee.data.remote.user.UserFirebaseDataSource
import com.example.mcoffee.data.remote.user.UserFirebaseDataSourceImpl
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSource
import com.example.mcoffee.data.remote.category.CategoryFirebaseDataSourceImpl
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSource
import com.example.mcoffee.data.remote.product.ProductFirebaseDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideUserFirebaseDataSource(auth: FirebaseAuth): UserFirebaseDataSource {
        return UserFirebaseDataSourceImpl(auth)
    }

    @Provides
    @Singleton
    fun provideCategoryFirebaseDataSource(databaseReference: DatabaseReference): CategoryFirebaseDataSource {
        return CategoryFirebaseDataSourceImpl(databaseReference)
    }

    @Provides
    @Singleton
    fun provideProductFirebaseDataSource(databaseReference: DatabaseReference) : ProductFirebaseDataSource {
        return ProductFirebaseDataSourceImpl(databaseReference)
    }

}