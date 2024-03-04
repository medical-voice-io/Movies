package io.android.movies.navigation

/** Маршруты экранов */
sealed class Screens(val route: String) {

    /** Маршрут экрана авторизации */
    data object Auth : Screens("auth")

    /** Маршрут экрана регистрации */
    data object Reg : Screens("reg")

    /** Маршрут экрана фильмов */
    data object Movies : Screens("movies")
}