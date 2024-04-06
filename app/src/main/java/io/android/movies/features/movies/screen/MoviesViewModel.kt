package io.android.movies.features.movies.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.movies.interactor.aggregate.MoviesAggregate
import io.android.movies.features.movies.interactor.aggregate.command.MoviesCommand
import io.android.movies.features.movies.interactor.domain.write.Favorite
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.projection.MoviesProjection
import io.android.movies.features.movies.screen.combiners.MoviesCombiner
import io.android.movies.features.movies.screen.mappers.MoviePreviewToMovieUiMapper
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    /**
     * Обработка изменения поискового запроса
     * @param query Поисковый запрос
     */
    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(searchQuery = query)
        }
    }

    /**
     * Обработка изменения избранного
     * @param movieUi Фильм
     */
    fun onFavoriteChanged(movie: MovieUi) {
        val command = if (movie.isFavorite) {
            MoviesCommand.RemoveFromFavorite(movieId = movie.id)
        } else {
            MoviesCommand.AddToFavorite(movieId = movie.id)
        }
        viewModelScope.launch {
            moviesAggregate.handle(command)
        }
    }

    /**
     * Обработка изменения фильтра
     */
    fun onFilterChange(needFilterMovies: Boolean) {
        _uiState.update { state ->
            state.copy(isFilterEnabled = needFilterMovies)
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
            combine(
                uiState,
                moviesProjection.getFavoriteFlow(),
                moviesProjection.getPagingMoviesFlow()
                    .cachedIn(viewModelScope),
            ) { state, favorites, pagingData ->
                MoviesCombiner(
                    isFilterEnabled = state.isFilterEnabled,
                    favorites = favorites,
                    pagingData = pagingData
                )
            }.updateState()
        }
    }

    private fun observeSearchMovies(
        searchQuery: String,
    ) {
        viewModelScope.launch {
            combine(
                uiState,
                moviesProjection.getFavoriteFlow(),
                moviesProjection.getSearchMoviesPagingFlow(searchQuery)
                    .cachedIn(viewModelScope),
            ) { state, favorites, pagingData ->
                MoviesCombiner(
                    isFilterEnabled = state.isFilterEnabled,
                    favorites = favorites,
                    pagingData = pagingData
                )
            }.updateState()
        }
    }

    private suspend fun Flow<MoviesCombiner>.updateState() =
        map { data ->
            val (isFilterEnabled, favorites, pagingData) = data
            pagingData
                .filter { movie ->
                    if (isFilterEnabled) {
                        favorites.any { it.movieId == movie.id }
                    } else {
                        true
                    }
                }
                .map { movie ->
                    moviePreviewToMovieUiMapper(favorites, movie)
                }
        }
            .flowOn(Dispatchers.IO)
            .collect { pagingData ->
                _moviesFlow.update {
                    pagingData
                }
            }
}