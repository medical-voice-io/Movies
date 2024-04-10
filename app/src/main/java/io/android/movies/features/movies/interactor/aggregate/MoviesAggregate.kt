package io.android.movies.features.movies.interactor.aggregate

import io.android.movies.features.movies.interactor.aggregate.command.MoviesCommand
import io.android.movies.features.movies.interactor.sync.MoviesProjector
import javax.inject.Inject

internal class MoviesAggregate @Inject constructor(
    private val moviesProjector: MoviesProjector,
) {

    suspend fun handle(command: MoviesCommand) {
        when (command) {
            is MoviesCommand.AddToFavorite -> moviesProjector.addToFavorite(command.movieId)
            is MoviesCommand.RemoveFromFavorite -> moviesProjector.removeFromFavorite(command.movieId)
        }
    }
}