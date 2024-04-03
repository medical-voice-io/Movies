package io.android.movies.features.movies.interactor.repository.local.room

import androidx.paging.PagingSource
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.room.database.MoviesDao
import io.android.movies.features.movies.interactor.repository.local.room.database.RemoteKeyDao
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity
import io.android.movies.features.movies.interactor.repository.local.room.mappers.MoviePreviewToEntityMapper
import io.android.movies.features.movies.interactor.repository.local.room.mappers.RemoteKeyEntityToDomainMapper
import io.android.movies.features.movies.interactor.repository.local.room.mappers.RemoteKeyToEntityMapper
import javax.inject.Inject

internal class MoviesCacheRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val moviePreviewToEntityMapper: MoviePreviewToEntityMapper,
    private val remoteKeyEntityToDomainMapper: RemoteKeyEntityToDomainMapper,
    private val remoteKeyToEntityMapper: RemoteKeyToEntityMapper,
) : MoviesCacheRepository {
    override fun getMoviesPagingSource(): PagingSource<Int, MovieEntity> =
        moviesDao.getMoviesPagingSource()

    override suspend fun insertMovies(movies: List<MoviePreview>) =
        moviesDao.insertMovies(
            movies = movies.map(moviePreviewToEntityMapper)
        )

    override suspend fun insetRemoteKeys(remoteKeys: List<RemoteKey>) =
        remoteKeyDao.insertRemoteKeys(
            remoteKeys = remoteKeys.map(remoteKeyToEntityMapper)
        )

    override suspend fun deleteMovies() = moviesDao.deleteMovies()

    override suspend fun deleteRemoteKeys() = remoteKeyDao.deleteRemoteKey()

    override suspend fun getRemoteKey(movieId: Int): RemoteKey? =
        remoteKeyDao.getRemoteKey(movieId)?.let(remoteKeyEntityToDomainMapper)
}