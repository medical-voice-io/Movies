package io.android.movies.features.movies.screen.mappers

import io.android.movies.features.movies.interactor.domain.write.Favorite
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.screen.models.MovieUi
import javax.inject.Inject

internal class MoviePreviewToMovieUiMapper @Inject constructor(
) : (List<Favorite>, MoviePreview) -> MovieUi {

    override fun invoke(
        favorites: List<Favorite>,
        moviePreview: MoviePreview,
    ): MovieUi = MovieUi(
        id = moviePreview.id,
        name = moviePreview.name,
        rating = moviePreview.rating,
        year = moviePreview.year,
        posterUrl = moviePreview.previewUrl,
        isFavorite = favorites.any { favorite -> favorite.movieId == moviePreview.id }
    )
}