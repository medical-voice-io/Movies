package io.android.movies.features.detail.interactor.repository.remote.dto.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.repository.remote.dto.response.ReviewDataResponse
import javax.inject.Inject

internal class ReviewDataResponseToMovieReviewList @Inject constructor(
    val itemMapper: ReviewItemResponseToMovieReview
) : (ReviewDataResponse) -> List<MovieReview> {

    override fun invoke(response: ReviewDataResponse): List<MovieReview> = response.items.map { itemMapper(it) }
}