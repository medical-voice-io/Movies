package io.android.movies.features.movies.interactor.repository.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity
import io.android.movies.features.movies.interactor.repository.local.room.entity.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class],
    version = 1
)
internal abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}