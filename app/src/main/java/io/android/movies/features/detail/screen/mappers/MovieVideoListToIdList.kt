package io.android.movies.features.detail.screen.mappers

import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import javax.inject.Inject

class MovieVideoListToIdList @Inject constructor(
    private val itemMapper: MovieVideoToId,
) : (List<MovieVideo>) -> List<String> {

    override fun invoke(videos: List<MovieVideo>): List<String> = videos.mapNotNull(itemMapper)
}