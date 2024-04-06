package io.android.movies.features.movies.interactor.repository.remote

import io.android.movies.features.movies.interactor.repository.remote.MoviesUrls.MOVIES
import io.android.movies.features.movies.interactor.repository.remote.MoviesUrls.SEARCH_MOVIES
import io.android.movies.features.movies.interactor.repository.remote.dto.response.MoviesDataResponse
import io.android.movies.features.movies.interactor.repository.remote.dto.response.SearchMoviesDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api для работы с фильмами
 */
internal interface MoviesApi {

    /**
     * Получить фильмы
     * @param page номер страницы
     */
    @GET(MOVIES)
    suspend fun getMovies(
        @Query("page") page: Int,
    ): MoviesDataResponse

    @GET(SEARCH_MOVIES)
    suspend fun searchMovies(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
    ): SearchMoviesDataResponse
}