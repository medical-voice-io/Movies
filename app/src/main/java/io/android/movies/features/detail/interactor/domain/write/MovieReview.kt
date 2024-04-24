package io.android.movies.features.detail.interactor.domain.write

data class MovieReview(
    val id: Int = 0,
    val positiveRating: Int? = null,
    val negativeRating: Int? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
)