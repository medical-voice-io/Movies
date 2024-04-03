package io.android.movies.features.movies.interactor.repository.local.room.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.room.entity.MovieEntity
import javax.inject.Inject

internal class MovieEntityToDomainMapper @Inject constructor(
) : (MovieEntity) -> MoviePreview {
    override fun invoke(entity: MovieEntity): MoviePreview = MoviePreview(
        id = entity.id,
        name = entity.name,
        rating = entity.rating,
        year = entity.year,
        previewUrl = entity.previewUrl,
        page = entity.page,
    )
}