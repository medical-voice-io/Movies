package io.android.movies.features.splash.screen.event

/** События экрана сплеша */
internal sealed interface SplashEvent {

    /** Открыть экран авторизации */
    data object OpenAuthScreen : SplashEvent

    /** Открыть экран фильмов */
    data object OpenMoviesScreen : SplashEvent
}