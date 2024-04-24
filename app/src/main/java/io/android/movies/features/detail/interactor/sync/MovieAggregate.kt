package io.android.movies.features.detail.interactor.sync

import io.android.movies.features.detail.interactor.command.MovieCommand
import io.android.movies.features.movies.interactor.aggregate.command.MoviesCommand
import javax.inject.Inject

internal class MovieAggregate @Inject constructor(
    private val movieProjector: MovieProjector,
) {

    suspend fun handle(command: MovieCommand) {
        when (command) {
            is MovieCommand.LoadMovie -> movieProjector.syncMovieDetails(command.id)
            is MovieCommand.LoadReviews -> movieProjector.syncMovieReviews(command.id)
            is MovieCommand.LoadVideos -> movieProjector.syncMovieVideos(command.id)
        }
    }
}