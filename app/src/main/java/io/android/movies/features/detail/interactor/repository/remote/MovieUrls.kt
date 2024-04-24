package io.android.movies.features.detail.interactor.repository.remote

internal object MovieUrls {

    const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    private const val API_VERSION = "/api/v2.2"

    const val MOVIE_ID = "id"
    const val MOVIE = "$API_VERSION/films/{$MOVIE_ID}"
    const val MOVIE_VIDEOS = "$API_VERSION/films/{$MOVIE_ID}/videos"
    const val MOVIE_REVIEWS = "$API_VERSION/films/{$MOVIE_ID}/reviews"
}