package io.android.movies.features.movies.interactor.repository.local.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.android.movies.features.movies.interactor.domain.write.Favorite
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesLocalRepository.Companion.CHILD_MOVIES
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesLocalRepository.Companion.CHILD_REMOTE_KEY
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class MoviesDatabaseRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) : MoviesLocalRepository {
    override suspend fun insetMovies(
        movies: List<MoviePreview>
    ): Unit = withContext(Dispatchers.IO) {
        val cachedMovies = suspendCoroutine { continuation ->
            getReference().child(CHILD_MOVIES).get()
                .addOnSuccessListener { snapshot ->
                    val cachedMovies = snapshot.children.mapNotNull { childSnapshot ->
                        childSnapshot.getValue(MoviePreview::class.java)
                    }
                    continuation.resume(cachedMovies)
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error insetMovies: $error")
                    continuation.resumeWithException(error)
                }
        }
        getReference().child(CHILD_MOVIES).setValue(cachedMovies + movies)
    }

    override suspend fun getMovies(
        page: Int,
    ): List<MoviePreview> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_MOVIES)
                .get()
                .addOnSuccessListener { snapshot ->
                    val movies = snapshot.children.mapNotNull { childSnapshot ->
                        childSnapshot.getValue(MoviePreview::class.java)
                    }
                    continuation.resume(movies.filter { movie -> movie.page == page })
                }
                .addOnFailureListener { error ->
                    Log.d("MoviesLocalRepository", "Error getMovies: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    override suspend fun clearMovies() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_MOVIES).setValue(null)
            continuation.resume(Unit)
        }
    }

    override suspend fun insertRemoteKeys(
        remoteKeys: List<RemoteKey>
    ): Unit = withContext(Dispatchers.IO) {
        val cachedRemoteKeys = suspendCoroutine { continuation ->
            getReference().child(CHILD_REMOTE_KEY).get()
                .addOnSuccessListener { snapshot ->
                    val cachedRemoteKeys = snapshot.children.mapNotNull { childSnapshot ->
                        childSnapshot.getValue(RemoteKey::class.java)
                    }
                    continuation.resume(cachedRemoteKeys)
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error insertRemoteKeys: $error")
                    continuation.resumeWithException(error)
                }
        }
        getReference().child(CHILD_REMOTE_KEY).setValue(cachedRemoteKeys + remoteKeys)
    }

    override suspend fun getRemoteKey(
        movieId: Int,
    ): RemoteKey? = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_REMOTE_KEY).get()
                .addOnSuccessListener { snapshot ->
                    val remoteKeys = snapshot.children.map { childSnapshot ->
                        childSnapshot.getValue(RemoteKey::class.java)
                    }
                    val remoteKey = remoteKeys.filterNotNull().find { remoteKey ->
                        remoteKey.moviesId == movieId
                    }
                    continuation.resume(remoteKey)
                }
                .addOnFailureListener { error ->
                    Log.d("MoviesLocalRepository", "Error getRemoteKeyByMovieId: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    override suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_REMOTE_KEY).setValue(null)
            continuation.resume(Unit)
        }
    }

    override fun getFavoriteFlow(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }

    private fun getReference(): DatabaseReference = database.getReference(
        "users/${auth.currentUser?.uid}"
    )
}