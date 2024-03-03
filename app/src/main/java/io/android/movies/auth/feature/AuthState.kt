package io.android.movies.auth.feature

/**
 * Состояние экрана авторизации
 * @property email Почта
 * @property password Пароль
 */
internal data class AuthState(
    val email: String = "",
    val password: String = "",
)