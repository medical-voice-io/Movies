package io.android.movies.features.movies.screen

import io.android.movies.features.movies.screen.models.MovieUi

/**
 * Состояние экрана списка фильмов
 */
internal sealed interface MoviesState {

    /**
     * Отобразить список фильмов
     */
    data object ShowMovies  : MoviesState

    /**
     * Загрузка списка фильмов
     */
    data object MainLoading : MoviesState

    /**
     * Ошибка загрузки списка фильмов
     */
    data object MainError : MoviesState
}
