package io.android.movies.features.movies.interactor.repository.local.room.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.movies.interactor.repository.local.room.MoviesCacheRepository
import io.android.movies.features.movies.interactor.repository.local.room.MoviesCacheRepositoryImpl
import io.android.movies.features.movies.interactor.repository.local.room.database.MoviesDao
import io.android.movies.features.movies.interactor.repository.local.room.database.MoviesDatabase
import io.android.movies.features.movies.interactor.repository.local.room.database.RemoteKeyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CacheDatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext applicationContext: Context,
    ): MoviesDatabase = Room.databaseBuilder(
        applicationContext,
        MoviesDatabase::class.java,
        "movies_database",
    ).build()

    @Provides
    @Singleton
    fun provideMoviesDao(
        moviesDatabase: MoviesDatabase,
    ): MoviesDao = moviesDatabase.moviesDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(
        moviesDatabase: MoviesDatabase,
    ): RemoteKeyDao = moviesDatabase.remoteKeyDao()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CacheRepositoryModule {

    @Binds
    @Singleton
    fun bindMoviesCacheRepository(
        repository: MoviesCacheRepositoryImpl,
    ): MoviesCacheRepository
}