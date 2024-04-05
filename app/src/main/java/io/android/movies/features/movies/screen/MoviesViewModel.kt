package io.android.movies.features.movies.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.movies.interactor.aggregate.MoviesAggregate
import io.android.movies.features.movies.interactor.projection.MoviesProjection
import io.android.movies.features.movies.screen.mappers.MoviePreviewToMovieUiMapper
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ViewModel экрана списка фильмов
 */
@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    moviesProjection: MoviesProjection,
    private val moviesAggregate: MoviesAggregate,
    moviePreviewToMovieUiMapper: MoviePreviewToMovieUiMapper,
) : ViewModel() {

    val moviesFlow: Flow<PagingData<MovieUi>> =
        moviesProjection.getPagingMoviesFlow()
            .map { pagingData ->
                pagingData.map(moviePreviewToMovieUiMapper)
            }
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
}