package io.android.movies.features.movies.interactor.aggregate.command

/**
 * Команда для работы с фильмами
 */
internal sealed interface MoviesCommand {

    /**
     * Добавить в избранное
     * @param movieId Идентификатор фильма
     */
    data class AddToFavorite(val movieId: Int) : MoviesCommand

    /**
     * Удалить из избранного
     * @param movieId Идентификатор фильма
     */
    data class RemoveFromFavorite(val movieId: Int) : MoviesCommand
}