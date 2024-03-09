package io.android.movies.features.auth.screen.event

/** События экрана авторизации */
internal sealed interface AuthEvent {

    /**
     * Показать сообщение
     * @property message Сообщение
     */
    data class ShowMessage(val message: String) : AuthEvent

    /**
     * Открыть экран списка фильмов
     */
    object OpenMoviesScreen : AuthEvent
}