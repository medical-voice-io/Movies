package io.android.movies.features.movies.interactor.sync

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.MoviesLocalRepository
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.remote.MoviesRemoteRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class MoviesRemoteMediator @Inject constructor(
    private val localRepository: MoviesLocalRepository,
    private val remoteRepository: MoviesRemoteRepository,
) : RemoteMediator<Int, MoviePreview>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviePreview>
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

            if (loadType == LoadType.REFRESH) {
                localRepository.clearMovies()
                localRepository.clearRemoteKeys()
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
            localRepository.insertRemoteKeys(remoteKeys)
            localRepository.insetMovies(
                movies.map { movie ->
                    movie.copy(page = loadPage)
                }
            )

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
        state: PagingState<Int, MoviePreview>
    ): RemoteKey? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { movieId ->
            localRepository.getRemoteKeyByMovieId(movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MoviePreview>
    ): RemoteKey? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { movie ->
        localRepository.getRemoteKeyByMovieId(movie.id)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MoviePreview>
    ): RemoteKey? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { movie ->
        localRepository.getRemoteKeyByMovieId(movie.id)
    }
}