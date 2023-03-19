package com.example.mcoffee.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return  Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabaseRef(): DatabaseReference {
        return Firebase.database.reference
    }

}