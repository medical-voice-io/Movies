package io.android.movies.features.movies.interactor.repository.remote.dto.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.remote.dto.response.SearchMoviesDataResponse
import javax.inject.Inject

internal class SearchMoviesDataResponseToDomainMapper @Inject constructor(
    private val searchMovieResponseToDomainMapper: SearchMovieResponseToDomainMapper,
) : (SearchMoviesDataResponse) -> List<MoviePreview> {
    override fun invoke(
        response: SearchMoviesDataResponse,
    ): List<MoviePreview> = response.movies.map(searchMovieResponseToDomainMapper)
}