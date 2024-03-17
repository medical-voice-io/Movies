package io.android.movies.features.movies.screen.models

/**
 * UI модель фильма
 * @property name название фильма
 * @property rating рейтинг фильма
 * @property year год выпуска фильма
 * @property posterUrl ссылка на постер фильма
 */
internal data class MovieUi(
    val name: String,
    val rating: Float,
    val year: Int,
    val posterUrl: String,
)