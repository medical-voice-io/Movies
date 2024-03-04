package io.android.movies.auth.feature.event

/** События экрана авторизации */
internal sealed interface AuthEvent {

    /**
     * Показать сообщение
     * @property message Сообщение
     */
    data class ShowMessage(val message: String) : AuthEvent
}