package io.android.movies.features.movies.interactor.repository.local

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.dto.MoviesType
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKeysType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class MoviesLocalRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) {

    // Movies

    fun insertMoviesForTransaction(
        currentData: MutableData,
        movies: List<MoviePreview>
    ) {
        val moviesChild = currentData.child(CHILD_MOVIES)
        val currentMovies = moviesChild.getValue(MoviesType()) ?: emptyList()
        moviesChild.value = currentMovies + movies
    }

    suspend fun insetMovies(movies: List<MoviePreview>) = withContext(Dispatchers.IO) {
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

    suspend fun getMovies(page: Int): List<MoviePreview> = withContext(Dispatchers.IO) {
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

    fun clearMoviesForTransaction(currentData: MutableData) {
        currentData.child(CHILD_MOVIES).value = null
    }

    suspend fun clearMovies() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_MOVIES).setValue(null)
            continuation.resume(Unit)
        }
    }

    // RemoteKey

    fun insertRemoteKeysForTransaction(
        currentData: MutableData, // TODO: возможно через это вставлять
        keys: List<RemoteKey>
    ) {
        val remoteKeysChild = currentData.child(CHILD_REMOTE_KEY)
        val remoteKeys = remoteKeysChild.getValue(RemoteKeysType()) ?: emptyList()
        remoteKeysChild.value = remoteKeys + keys
    }

    suspend fun insertRemoteKeys(
        remoteKeys: List<RemoteKey>
    ) = withContext(Dispatchers.IO) {
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

    suspend fun getRemoteKeyByMovieId(
        movieId: Int,
    ) = withContext(Dispatchers.IO) {
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

    fun clearRemoteKeysForTransaction(currentData: MutableData) {
        currentData.child(CHILD_REMOTE_KEY).value = null
    }

    suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getReference().child(CHILD_REMOTE_KEY).setValue(null)
            continuation.resume(Unit)
        }
    }

    fun getReference(): DatabaseReference = database.getReference(
        "users/${auth.currentUser?.uid}"
    )

    private companion object {
        const val CHILD_REMOTE_KEY = "remote_key"
        const val CHILD_MOVIES = "movies"
    }
}