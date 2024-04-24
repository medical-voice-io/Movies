package io.android.movies.features.detail.interactor.sync

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import io.android.movies.features.detail.interactor.repository.local.firebase.MovieLocalRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.DatabaseRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MovieProjection @Inject constructor(
    @DatabaseRepository private val localRepository: MovieLocalRepository,
) {
    fun getMovieDetails(id: Int): Flow<MovieDetails> = localRepository.getMovieDetails(id)
    fun getMovieVideos(id: Int): Flow<List<MovieVideo>> = localRepository.getMovieVideos(id)
    fun getMovieReviews(id: Int): Flow<List<MovieReview>> = localRepository.getMovieReviews(id)
}