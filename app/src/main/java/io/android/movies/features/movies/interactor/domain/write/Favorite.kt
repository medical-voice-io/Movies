package io.android.movies.features.movies.interactor.domain.write

/**
 * Модель избранного фильма
 * @param movieId идентификатор фильма
 */
internal data class Favorite(
    val movieId: Int = 0,
)