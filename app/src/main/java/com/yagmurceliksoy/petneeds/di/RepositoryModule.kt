package com.yagmurceliksoy.petneeds.di

import com.google.firebase.auth.FirebaseAuth
import com.yagmurceliksoy.petneeds.data.repository.UserRepository
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
    fun provideUserRepository(firebaseAuth: FirebaseAuth): UserRepository =
        UserRepository(firebaseAuth)
}