package io.android.movies.auth.interactor.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.auth.interactor.repository.AuthLocalRepository
import io.android.movies.auth.interactor.repository.AuthLocalRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthRepositoryModule {

    @Binds
    @Singleton
    fun bindAuthLocalRepository(
        repository: AuthLocalRepositoryImpl
    ): AuthLocalRepository
}