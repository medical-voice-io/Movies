package io.android.movies.features.detail.interactor.sync

import io.android.movies.features.detail.interactor.repository.local.firebase.MovieLocalRepository
import io.android.movies.features.detail.interactor.repository.remote.MovieRemoteRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.DatabaseRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.di.FirestoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MovieProjector @Inject constructor(
    private val remoteRepository: MovieRemoteRepository,
    @DatabaseRepository private val localRepository: MovieLocalRepository,
) {

    suspend fun syncMovieDetails(id: Int) {
        val filmDetails = remoteRepository.getMovie(id)
        localRepository.saveDetails(filmDetails)
    }
    suspend fun syncMovieReviews(id: Int) {
        val reviews = remoteRepository.getReviews(id)
        localRepository.saveReviews(id, reviews)
    }
    suspend fun syncMovieVideos(id: Int) {
        val videos = remoteRepository.getVideos(id)
        localRepository.saveVideos(id, videos)
    }
}