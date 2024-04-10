package io.android.movies.features.movies.interactor.projection

import io.android.movies.features.movies.interactor.sync.MoviesProjector
import javax.inject.Inject

internal class MoviesProjection @Inject constructor(
    private val moviesProjector: MoviesProjector,
) {

    /**
     * Получить фильмы
     */
    fun getPagingMoviesFlow() = moviesProjector.getMoviesFlow()

    /**
     * Поиск фильмов
     * @param keyword Ключевое слово
     */
    fun getSearchMoviesPagingFlow(
        keyword: String,
    ) = moviesProjector.searchMovies(keyword)

    /**
     * Получить избранные фильмы
     */
    fun getFavoriteFlow() = moviesProjector.getFavoriteFlow()
}