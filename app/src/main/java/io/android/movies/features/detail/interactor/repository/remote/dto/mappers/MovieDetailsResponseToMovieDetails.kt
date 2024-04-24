package io.android.movies.features.detail.interactor.repository.remote.dto.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.repository.remote.dto.response.MovieDetailsResponse
import javax.inject.Inject

internal class MovieDetailsResponseToMovieDetails @Inject constructor() :
        (MovieDetailsResponse) -> MovieDetails {

    override fun invoke(response: MovieDetailsResponse): MovieDetails = MovieDetails(
        id = response.id,
        name = response.nameRu ?: response.nameEn ?: response.nameOriginal,
        rating = response.rating,
        year = response.year,
        posterUrl = response.posterUrl,
        reviewCounts = response.reviewCounts,
        slogan = response.slogan,
        description = response.description,
    )
}