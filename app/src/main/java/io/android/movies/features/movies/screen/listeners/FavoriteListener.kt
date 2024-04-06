package io.android.movies.features.movies.screen.listeners

import io.android.movies.features.movies.screen.models.MovieUi

internal fun interface FavoriteListener {
    fun onChange(movie: MovieUi)
}