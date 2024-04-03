package io.android.movies.features.movies.interactor.sync

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesLocalRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.FirestoreRepository
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.room.MoviesCacheRepository
import io.android.movies.features.movies.interactor.repository.local.room.database.MoviesDatabase
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity
import io.android.movies.features.movies.interactor.repository.remote.MoviesRemoteRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class MoviesRemoteMediator @Inject constructor(
    @FirestoreRepository private val localRepository: MoviesLocalRepository,
    private val moviesDatabase: MoviesDatabase,
    private val moviesCacheRepository: MoviesCacheRepository,
    private val remoteRepository: MoviesRemoteRepository,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val loadPage: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null,
                )
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null,
                )
            }
        }

        return try {
            val response = remoteRepository.getMovies(page = loadPage)
            val movies = response.movies
            val endOfPaginationReached = response.movies.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesCacheRepository.deleteMovies()
                    moviesCacheRepository.deleteRemoteKeys()
                }
                val prevKey = if (loadPage > 1) loadPage - 1 else null
                val nextKey = if (endOfPaginationReached) null else loadPage + 1
                val remoteKeys = movies.map { movie ->
                    RemoteKey(
                        moviesId = movie.id,
                        prevKey = prevKey,
                        currentPage = loadPage,
                        nextKey = nextKey,
                    )
                }
                moviesCacheRepository.insetRemoteKeys(remoteKeys)
                moviesCacheRepository.insertMovies(
                    movies = movies.map { movie ->
                        movie.copy(page = loadPage)
                    }
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (ioException: IOException) {
            MediatorResult.Error(ioException)
        } catch (networkException: HttpException) {
            MediatorResult.Error(networkException)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { movieId ->
            moviesCacheRepository.getRemoteKey(movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { movie ->
        moviesCacheRepository.getRemoteKey(movie.id)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { movie ->
        moviesCacheRepository.getRemoteKey(movie.id)
    }
}