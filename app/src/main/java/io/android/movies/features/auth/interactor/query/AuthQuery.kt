package io.android.movies.features.auth.interactor.query

/** Запрос экрана авторизации */
internal sealed interface AuthQuery {

    /** Запрос получения текущего пользователя */
    data object GetCurrentUser

    /** Подписаться на результат авторизации */
    data object ObserveAuthResult
}
