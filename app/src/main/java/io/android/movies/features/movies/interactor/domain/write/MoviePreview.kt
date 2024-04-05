package io.android.movies.features.movies.interactor.domain.write

/**
 * Превью фильма
 * @property id идентификатор
 * @property name название
 * @property rating оценка
 * @property year год
 * @property previewUrl ссылка на превью
 */
internal data class MoviePreview(
    val id: Int = 0,
    val name: String = "",
    val rating: Double = 0.0,
    val year: Int = 0,
    val previewUrl: String = "",
    val page: Int = 0,
)