package io.android.movies.features.detail.interactor.repository.remote

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import io.android.movies.features.detail.interactor.repository.remote.dto.mappers.MovieDetailsResponseToMovieDetails
import io.android.movies.features.detail.interactor.repository.remote.dto.mappers.ReviewDataResponseToMovieReviewList
import io.android.movies.features.detail.interactor.repository.remote.dto.mappers.VideoDataResponseToMovieVideoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRemoteRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieMapper: MovieDetailsResponseToMovieDetails,
    private val reviewMapper: ReviewDataResponseToMovieReviewList,
    private val videoMapper: VideoDataResponseToMovieVideoList,
) {

    suspend fun getMovie(id: Int): MovieDetails = withContext(Dispatchers.IO) {
        runBlocking {
            val response = movieApi.getMovie(id)
            movieMapper(response)
        }
    }
    suspend fun getReviews(id: Int): List<MovieReview> = withContext(Dispatchers.IO) {
        runBlocking {
            val response = movieApi.getReviews(id)
            reviewMapper(response)
        }
    }
    suspend fun getVideos(id: Int): List<MovieVideo> = withContext(Dispatchers.IO) {
        runBlocking {
            val response = movieApi.getVideos(id)
            videoMapper(response)
        }
    }
}