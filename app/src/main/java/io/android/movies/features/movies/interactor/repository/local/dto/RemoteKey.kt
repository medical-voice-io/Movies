package io.android.movies.features.movies.interactor.repository.local.dto

import io.android.movies.features.movies.interactor.domain.write.MoviePreview

internal class RemoteKey(
    val moviesId: Int = 0,
    val prevKey: Int? = null,
    val currentPage: Int = 0,
    val nextKey: Int? = null,
)