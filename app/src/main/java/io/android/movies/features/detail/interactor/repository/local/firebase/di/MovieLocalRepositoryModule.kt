package io.android.movies.features.detail.interactor.repository.local.firebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.detail.interactor.repository.local.firebase.MovieDatabaseRepository
import io.android.movies.features.detail.interactor.repository.local.firebase.MovieFirestoreRepository
import io.android.movies.features.detail.interactor.repository.local.firebase.MovieLocalRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.DatabaseRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.FirestoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MovieLocalRepositoryModule {

    @Binds
    @Singleton
    @DatabaseRepository
    fun bindMovieDatabaseRepository(
        repository: MovieDatabaseRepository,
    ): MovieLocalRepository

    @Binds
    @Singleton
    @FirestoreRepository
    fun bindMovieFirestoreRepository(
        repository: MovieFirestoreRepository,
    ): MovieLocalRepository
}