package io.android.movies.features.detail.interactor.repository.remote

import io.android.movies.features.detail.interactor.repository.remote.MovieUrls.MOVIE
import io.android.movies.features.detail.interactor.repository.remote.MovieUrls.MOVIE_ID
import io.android.movies.features.detail.interactor.repository.remote.MovieUrls.MOVIE_REVIEWS
import io.android.movies.features.detail.interactor.repository.remote.MovieUrls.MOVIE_VIDEOS
import io.android.movies.features.detail.interactor.repository.remote.dto.response.MovieDetailsResponse
import io.android.movies.features.detail.interactor.repository.remote.dto.response.ReviewDataResponse
import io.android.movies.features.detail.interactor.repository.remote.dto.response.VideoDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MovieApi {

    @GET(MOVIE)
    suspend fun getMovie(
        @Path(MOVIE_ID) id: Int,
    ): MovieDetailsResponse

    @GET(MOVIE_VIDEOS)
    suspend fun getVideos(
        @Path(MOVIE_ID) id: Int,
    ): VideoDataResponse

    @GET(MOVIE_REVIEWS)
    suspend fun getReviews(
        @Path(MOVIE_ID) id: Int,
    ): ReviewDataResponse
}