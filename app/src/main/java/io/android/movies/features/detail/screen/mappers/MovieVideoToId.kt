package io.android.movies.features.detail.screen.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import io.android.movies.features.detail.interactor.repository.remote.dto.response.MovieDetailsResponse
import javax.inject.Inject

class MovieVideoToId @Inject constructor() :
        (MovieVideo) -> String? {

    override fun invoke(video: MovieVideo): String? =
        video.url?.split("/")?.last()?.split("v=")?.last()?.split("&")?.first()?.split("?")?.first()
}