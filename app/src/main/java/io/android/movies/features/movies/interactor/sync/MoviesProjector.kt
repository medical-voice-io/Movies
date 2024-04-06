package io.android.movies.features.movies.interactor.sync

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.room.MoviesCacheRepository
import io.android.movies.features.movies.interactor.repository.local.room.mappers.MovieEntityToDomainMapper
import io.android.movies.features.movies.interactor.repository.remote.MoviesRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MoviesProjector @Inject constructor(
    private val moviesRemoteMediator: MoviesRemoteMediator,
    private val moviesCacheRepository: MoviesCacheRepository,
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val movieEntityToDomainMapper: MovieEntityToDomainMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMoviesFlow(): Flow<PagingData<MoviePreview>> = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_NUMBER,
            initialLoadSize = DEFAULT_PAGE_NUMBER,
            enablePlaceholders = false,
            prefetchDistance = 2,
        ),
        remoteMediator = moviesRemoteMediator,
        pagingSourceFactory = { moviesCacheRepository.getMoviesPagingSource() }
    ).flow.map { pagingData ->
        pagingData.map(movieEntityToDomainMapper)
    }

    fun searchMovies(
        keyword: String,
    ): Flow<PagingData<MoviePreview>> = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_NUMBER,
            initialLoadSize = DEFAULT_PAGE_NUMBER,
            enablePlaceholders = false,
            prefetchDistance = 2,
        ),
        pagingSourceFactory = {
            SearchMoviesPagingSource(
                moviesRemoteRepository = moviesRemoteRepository,
                searchKeyword = keyword,
            )
        }
    ).flow

    private companion object {
        const val DEFAULT_PAGE_NUMBER = 20
    }
}