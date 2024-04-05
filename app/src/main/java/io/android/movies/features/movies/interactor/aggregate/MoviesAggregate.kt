package io.android.movies.features.movies.interactor.aggregate

import io.android.movies.features.movies.interactor.sync.MoviesProjector
import javax.inject.Inject

internal class MoviesAggregate @Inject constructor(
    private val moviesProjector: MoviesProjector,
) {
}