package io.android.movies.features.movies.interactor.repository.local.room.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.android.movies.features.movies.interactor.repository.local.room.entity.MOVIE_TABLE_NAME
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity

@Dao
internal interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM $MOVIE_TABLE_NAME ORDER BY page")
    fun getMoviesPagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM $MOVIE_TABLE_NAME")
    suspend fun deleteMovies()
}