package io.android.movies.auth.feature

/**
 * Состояние экрана авторизации
 * @property email Почта пользователя
 * @property password Пароль пользователя
 * @property isLoading Загрузка экрана
 */
internal data class AuthState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)