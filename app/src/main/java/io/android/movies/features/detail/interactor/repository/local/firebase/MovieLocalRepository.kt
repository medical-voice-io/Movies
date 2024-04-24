package io.android.movies.features.detail.interactor.repository.local.firebase

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    fun getMovieDetails(id: Int): Flow<MovieDetails>
    fun getMovieReviews(id: Int): Flow<List<MovieReview>>
    fun getMovieVideos(id: Int): Flow<List<MovieVideo>>
    suspend fun saveDetails(movie: MovieDetails)
    suspend fun saveReviews(id: Int, reviews: List<MovieReview>)
    suspend fun saveVideos(id: Int, videos: List<MovieVideo>)
}