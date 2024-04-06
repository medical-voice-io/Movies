package io.android.movies.features.movies.interactor.repository.remote

internal object MoviesUrls {

    const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    private const val API_VERSION = "/api/v2.2"

    const val MOVIES = "$API_VERSION/films"
    const val SEARCH_MOVIES = "$MOVIES/search-by-keyword"
}