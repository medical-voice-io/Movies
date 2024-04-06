package io.android.movies.features.movies.interactor.repository.remote.dto.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.remote.dto.response.SearchMovieResponse
import javax.inject.Inject

internal class SearchMovieResponseToDomainMapper @Inject constructor(
) : (SearchMovieResponse) -> MoviePreview {
    override fun invoke(
        response: SearchMovieResponse,
    ): MoviePreview = MoviePreview(
        id = response.id,
        name = response.nameRu ?: response.nameEn ?: "",
        rating = response.rating.toDoubleOrNull() ?: 0.0,
        year = response.year.toIntOrNull() ?: 0,
        previewUrl = response.posterUrlPreview,
    )
}