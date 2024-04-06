package io.android.movies.features.movies.interactor.repository.remote

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.domain.write.MoviesData
import io.android.movies.features.movies.interactor.repository.remote.dto.mappers.MoviesDataResponseToMoviePreviewMapper
import io.android.movies.features.movies.interactor.repository.remote.dto.mappers.SearchMoviesDataResponseToDomainMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MoviesRemoteRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDataResponseToMoviePreviewMapper: MoviesDataResponseToMoviePreviewMapper,
    private val searchMoviesDataResponseToDomainMapper: SearchMoviesDataResponseToDomainMapper,
) {

    /**
     * Получить фильмы
     * @param page номер страницы
     */
    suspend fun getMovies(page: Int): Result<MoviesData> = withContext(Dispatchers.IO) {
        runCatching {
            val response = moviesApi.getMovies(page)
            moviesDataResponseToMoviePreviewMapper(response)
        }
    }

    suspend fun searchMovies(
        keyword: String,
        page: Int,
    ): Result<List<MoviePreview>> = withContext(Dispatchers.IO) {
        runCatching {
            val response = moviesApi.searchMovies(keyword, page)
            searchMoviesDataResponseToDomainMapper(response)
        }
    }
}