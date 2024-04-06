package io.android.movies.features.movies.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.movies.interactor.aggregate.MoviesAggregate
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.projection.MoviesProjection
import io.android.movies.features.movies.screen.mappers.MoviePreviewToMovieUiMapper
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel экрана списка фильмов
 */
@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val moviesProjection: MoviesProjection,
    private val moviesAggregate: MoviesAggregate,
    private val moviePreviewToMovieUiMapper: MoviePreviewToMovieUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesState())
    val uiState: StateFlow<MoviesState> = _uiState.asStateFlow()

    private val _moviesFlow = MutableStateFlow<PagingData<MovieUi>>(PagingData.empty())
    val moviesFlow: Flow<PagingData<MovieUi>> = _moviesFlow.asStateFlow()

    init {
        observeMovies()
        observeSearchQuery()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(searchQuery = query)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            uiState
                .debounce(500L)
                .collect { state ->
                    if (state.searchQuery.isEmpty()) {
                        observeMovies()
                    } else {
                        observeSearchMovies(state.searchQuery)
                    }
                }
        }
    }

    private fun observeMovies() {
        viewModelScope.launch {
            moviesProjection
                .getPagingMoviesFlow()
                .updateState()
        }
    }

    private fun observeSearchMovies(
        searchQuery: String,
    ) {
        viewModelScope.launch {
            moviesProjection
                .getSearchMoviesPagingFlow(searchQuery)
                .updateState()
        }
    }

    private suspend fun Flow<PagingData<MoviePreview>>.updateState() =
        map { pagingData -> pagingData.map(moviePreviewToMovieUiMapper) }
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
            .collect { pagingData ->
                _moviesFlow.update {
                    pagingData
                }
            }
}