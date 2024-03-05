package io.android.movies.features.auth.interactor.domain.write

/**
 * Модель данных для авторизации
 * @property email Почта
 * @property password Пароль
 */
data class UserLogin(
    val email: String,
    val password: String,
)