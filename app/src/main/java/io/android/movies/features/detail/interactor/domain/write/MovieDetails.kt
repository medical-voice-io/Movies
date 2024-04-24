package io.android.movies.features.detail.interactor.domain.write

data class MovieDetails(
    val id: Int = 0,
    val name: String? = null,
    val rating: Float? = null,
    val year: Int? = null,
    val posterUrl: String? = null,
    val reviewCounts: Int? = null,
    val slogan: String? = null,
    val description: String? = null,
)