package io.android.movies.features.movies.interactor.sync

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.MoviesLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class MoviesPagingSource @Inject constructor(
    private val localRepository: MoviesLocalRepository,
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

        val movies = localRepository.getMovies(page = currentPage)

        val prevKey = if (currentPage > 1) currentPage - 1 else null
        val nextKey = if (movies.isNotEmpty()) currentPage + 1 else null

        LoadResult.Page(
            data = movies,
            prevKey = prevKey,
            nextKey = nextKey,
        )
    } catch (error: Exception) {
        LoadResult.Error(error)
    }
}