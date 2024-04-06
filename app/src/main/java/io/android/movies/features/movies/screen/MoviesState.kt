package io.android.movies.features.movies.screen

internal data class MoviesState(
    val searchQuery: String = "",
    val isFilterEnabled: Boolean = false,
)