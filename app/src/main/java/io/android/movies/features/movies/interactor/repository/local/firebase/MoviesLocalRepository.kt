package io.android.movies.features.movies.interactor.repository.local.firebase

import io.android.movies.features.movies.interactor.domain.write.Favorite
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с локальными данными фильмов
 */
internal interface MoviesLocalRepository {

    /**
     * Добавить фильмы в базу данных
     * @param movies список фильмов
     */
    suspend fun insetMovies(movies: List<MoviePreview>)

    /**
     * Получить фильмы из базы данных
     * @param page номер страницы
     */
    suspend fun getMovies(page: Int): List<MoviePreview>

    /**
     * Очистить фильмы из базы данных
     */
    suspend fun clearMovies()

    /**
     * Добавить ключи удаленных фильмов в базу данных
     * @param remoteKeys список ключей удаленных фильмов
     */
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKey>)

    /**
     * Получить ключ удаленного фильма
     * @param movieId идентификатор фильма
     */
    suspend fun getRemoteKey(movieId: Int): RemoteKey?

    /**
     * Очистить ключи удаленных фильмов из базы данных
     */
    suspend fun clearRemoteKeys()

    /**
     * Получить избранное
     */
    fun getFavoriteFlow(): Flow<List<Favorite>>

    /**
     * Добавить фильм в избранное
     * @param movieId идентификатор фильма
     */
    suspend fun addToFavorite(movieId: Int)

    /**
     * Удалить фильм из избранного
     * @param movieId идентификатор фильма
     */
    suspend fun removeFromFavorite(movieId: Int)

    companion object {
        const val CHILD_REMOTE_KEY = "remote_key"
        const val CHILD_MOVIES = "movies"
        const val CHILD_FAVORITE = "favorite"
    }
}
