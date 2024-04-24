package io.android.movies.features.detail.interactor.repository.remote.dto.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import io.android.movies.features.detail.interactor.repository.remote.dto.response.VideoDataResponse
import javax.inject.Inject

class VideoDataResponseToMovieVideoList @Inject constructor(
    val itemMapper: VideoItemResponseToMovieVideo
) : (VideoDataResponse) -> List<MovieVideo> {

    override fun invoke(response: VideoDataResponse): List<MovieVideo> = response.items.map { itemMapper(it) }
}