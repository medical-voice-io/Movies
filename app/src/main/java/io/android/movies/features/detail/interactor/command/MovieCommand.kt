package io.android.movies.features.detail.interactor.command

sealed interface MovieCommand {
    data class LoadMovie(val id: Int): MovieCommand
    data class LoadReviews(val id: Int): MovieCommand
    data class LoadVideos(val id: Int): MovieCommand
}