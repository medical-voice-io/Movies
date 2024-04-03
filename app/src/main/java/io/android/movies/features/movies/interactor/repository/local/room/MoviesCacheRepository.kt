package io.android.movies.features.movies.interactor.repository.local.room

import androidx.paging.PagingSource
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity

internal interface MoviesCacheRepository {

    fun getMoviesPagingSource(): PagingSource<Int, MovieEntity>

    suspend fun insertMovies(movies: List<MoviePreview>)

    suspend fun insetRemoteKeys(remoteKeys: List<RemoteKey>)

    suspend fun deleteMovies()

    suspend fun deleteRemoteKeys()

    suspend fun getRemoteKey(movieId: Int): RemoteKey?
}