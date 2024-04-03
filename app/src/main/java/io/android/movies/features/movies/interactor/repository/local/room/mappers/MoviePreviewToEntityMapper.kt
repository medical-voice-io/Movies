package io.android.movies.features.movies.interactor.repository.local.room.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity
import javax.inject.Inject

internal class MoviePreviewToEntityMapper @Inject constructor(
) : (MoviePreview) -> MovieEntity {
    override fun invoke(movie: MoviePreview): MovieEntity = MovieEntity(
        id = movie.id,
        name = movie.name,
        rating = movie.rating,
        previewUrl = movie.previewUrl,
        year = movie.year,
        page = movie.page,
    )
}