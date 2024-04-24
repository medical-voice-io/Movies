package io.android.movies.features.detail.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.android.movies.features.detail.interactor.command.MovieCommand
import io.android.movies.features.detail.interactor.sync.MovieAggregate
import io.android.movies.features.detail.interactor.sync.MovieProjection
import io.android.movies.features.detail.screen.mappers.MovieVideoListToIdList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DetailViewModel @AssistedInject constructor(
    private val movieAggregate: MovieAggregate,
    private val movieProjection: MovieProjection,
    private val movieVideoMapper: MovieVideoListToIdList,
    @Assisted val id: Int,
) : ViewModel() {

    private val _movieState = MutableStateFlow<MovieState>(MovieState.Loading)
    val movieState: StateFlow<MovieState> = _movieState

    init {
        setupListeners()
    }

    fun screenLoaded() {
        viewModelScope.launch {
            movieAggregate.handle(MovieCommand.LoadMovie(id))
        }
        viewModelScope.launch {
            movieAggregate.handle(MovieCommand.LoadVideos(id))
        }
        viewModelScope.launch {
            movieAggregate.handle(MovieCommand.LoadReviews(id))
        }
    }

    private fun setupListeners() {
        viewModelScope.launch {
            combine(
                movieProjection.getMovieDetails(id),
                movieProjection.getMovieVideos(id)
                    .map {
                        val list = it.filter { it.source?.equals("youtube", true) == true }
                        movieVideoMapper(list)
                    },
                movieProjection.getMovieReviews(id),
            ) { details, videos, reviews ->
                MovieState.Loaded(details, videos, reviews)
            }
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    _movieState.update { state }
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}