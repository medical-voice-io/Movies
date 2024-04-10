package io.android.movies.features.movies.interactor.sync

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.remote.MoviesRemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchMoviesPagingSource(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val searchKeyword: String,
) : PagingSource<Int, MoviePreview>() {

    override fun getRefreshKey(
        state: PagingState<Int, MoviePreview>
    ): Int? = state.anchorPosition?.let { position ->
        val anchorPage = state.closestItemToPosition(position)
        anchorPage?.page
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MoviePreview> = try {
        val currentPage = params.key ?: 1

        val movies = moviesRemoteRepository.searchMovies(
            keyword = searchKeyword,
            page = currentPage,
        ).getOrDefault(emptyList())

        if (movies.isNotEmpty()) {
            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage > 1) currentPage - 1 else null,
                nextKey = if (movies.isNotEmpty()) currentPage + 1 else null,
            )
        } else {
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            )
        }
    } catch (error: Exception) {
        LoadResult.Error(error)
    }
}