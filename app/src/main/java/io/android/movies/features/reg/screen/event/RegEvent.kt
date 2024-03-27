package io.android.movies.features.reg.screen.event

import androidx.annotation.StringRes

internal sealed interface RegEvent {

    /**
     * Показать сообщение
     * @property message Сообщение
     */
    data class ShowMessageRes(@StringRes val messageRes: Int) : RegEvent

    data class ShowMessage(val message: String) : RegEvent

    /**
     * Открыть экран списка фильмов
     */
    data object OpenMoviesScreen : RegEvent
}