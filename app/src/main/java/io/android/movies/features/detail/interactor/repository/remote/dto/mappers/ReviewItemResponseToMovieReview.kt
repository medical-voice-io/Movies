package io.android.movies.features.detail.interactor.repository.remote.dto.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.repository.remote.dto.response.MovieDetailsResponse
import io.android.movies.features.detail.interactor.repository.remote.dto.response.ReviewItemResponse
import javax.inject.Inject

internal class ReviewItemResponseToMovieReview @Inject constructor() :
        (ReviewItemResponse) -> MovieReview {

    override fun invoke(response: ReviewItemResponse): MovieReview = MovieReview(
        id = response.id,
        positiveRating = response.positiveRating,
        negativeRating = response.negativeRating,
        author = response.author,
        title = response.title,
        description = response.description,
    )
}