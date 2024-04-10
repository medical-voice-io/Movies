package io.android.movies.features.movies.screen.combiners

import androidx.paging.PagingData
import io.android.movies.features.movies.interactor.domain.write.Favorite
import io.android.movies.features.movies.interactor.domain.write.MoviePreview

internal data class MoviesCombiner(
    val isFilterEnabled: Boolean,
    val favorites: List<Favorite>,
    val pagingData: PagingData<MoviePreview>,
)