package com.example.mcoffee.di

import com.example.mcoffee.data.remote.UserFirebaseDataSource
import com.example.mcoffee.data.repo.UserRepository
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
}