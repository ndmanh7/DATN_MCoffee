package com.example.mcoffee.di

import com.example.mcoffee.data.remote.UserFirebaseDataSource
import com.example.mcoffee.data.remote.UserFirebaseDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
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

}