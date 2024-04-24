package io.android.movies.features.detail.interactor.repository.remote.dto.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import io.android.movies.features.detail.interactor.repository.remote.dto.response.VideoItemResponse
import javax.inject.Inject

class VideoItemResponseToMovieVideo @Inject constructor() :
        (VideoItemResponse) -> MovieVideo {

    override fun invoke(response: VideoItemResponse): MovieVideo = MovieVideo(
        url = response.url,
        name = response.name,
        source = response.site,
    )
}