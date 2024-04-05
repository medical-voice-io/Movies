package io.android.movies.features.movies.interactor.repository.local.firebase

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey

internal interface MoviesLocalRepository {
    suspend fun insetMovies(movies: List<MoviePreview>)
    suspend fun getMovies(page: Int): List<MoviePreview>
    suspend fun clearMovies()
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKey(movieId: Int): RemoteKey?
    suspend fun clearRemoteKeys()

    companion object {
        const val CHILD_REMOTE_KEY = "remote_key"
        const val CHILD_MOVIES = "movies"
    }
}
