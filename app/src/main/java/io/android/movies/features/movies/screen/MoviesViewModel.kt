package io.android.movies.features.movies.screen

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel экрана списка фильмов
 */
@HiltViewModel
internal class MoviesViewModel @Inject constructor() : ViewModel() {

    val moviesFlow: Flow<PagingData<MovieUi>> = MutableSharedFlow()

    private val _moviesState = MutableStateFlow(MoviesState.MainLoading)
    val moviesState: StateFlow<MoviesState> get() = _moviesState.asStateFlow()

    /**
     * Обновление экрана
     */
    fun refresh() {
        // TODO: реализовать перезагрузку экрана
    }
}