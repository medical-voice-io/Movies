package io.android.movies.features.auth.interactor.command

/** Комманды экрана авторизации */
internal sealed interface AuthCommand {

    /**
     * Комманда авторизации
     * @property email Почта
     * @property password Пароль
     */
    data class Login(
        val email: String,
        val password: String,
    ) : AuthCommand
}
