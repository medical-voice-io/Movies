package io.android.movies.features.detail.screen

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo

sealed interface MovieState {
    data object Loading : MovieState
    data class Loaded(
        val details: MovieDetails,
        val videos: List<String>,
        val reviews: List<MovieReview>,
    ) : MovieState
}