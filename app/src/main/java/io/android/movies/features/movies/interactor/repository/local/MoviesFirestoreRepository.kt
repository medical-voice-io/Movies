package io.android.movies.features.movies.interactor.repository.local

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.MoviesLocalRepository.Companion.CHILD_MOVIES
import io.android.movies.features.movies.interactor.repository.local.MoviesLocalRepository.Companion.CHILD_REMOTE_KEY
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class MoviesFirestoreRepository @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : MoviesLocalRepository {

    override suspend fun insetMovies(
        movies: List<MoviePreview>,
    ) = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            val userDocument = getUserDocument()
            val batch = database.batch()
            movies.forEach { movie ->
                val movieDocRef = userDocument.collection(CHILD_MOVIES).document()
                batch.set(movieDocRef, movie)
            }
            batch.commit()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error insetMovies: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    override suspend fun getMovies(
        page: Int,
    ): List<MoviePreview> = withContext(Dispatchers.IO) {
        val snapshot = getUserDocument()
            .collection(CHILD_MOVIES)
            .whereEqualTo("page", page)
            .get()
            .await()
        snapshot.toObjects(MoviePreview::class.java)
    }

    override suspend fun clearMovies() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getUserDocument()
                .collection(CHILD_MOVIES)
                .get()
                .addOnSuccessListener { snapshot ->
                    val batch = database.batch()
                    snapshot.documents.forEach { document ->
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener { error ->
                            Log.e("MoviesLocalRepository", "Error clearMovies: $error")
                            continuation.resumeWithException(error)
                        }
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error clearMovies: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    override suspend fun insertRemoteKeys(
        remoteKeys: List<RemoteKey>,
    ) = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            val userDocument = getUserDocument()
            val batch = database.batch()

            remoteKeys.forEach { remoteKey ->
                val remoteKeyDocRef = userDocument.collection(CHILD_REMOTE_KEY).document()
                batch.set(remoteKeyDocRef, remoteKey)
            }
            batch.commit()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error insertRemoteKeys: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    override suspend fun getRemoteKey(
        movieId: Int,
    ): RemoteKey? = withContext(Dispatchers.IO) {
        getUserDocument()
            .collection(CHILD_REMOTE_KEY)
            .whereEqualTo("moviesId", movieId)
            .get()
            .await()
            .toObjects(RemoteKey::class.java)
            .firstOrNull()
    }

    override suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            getUserDocument()
                .collection(CHILD_REMOTE_KEY)
                .get()
                .addOnSuccessListener { snapshot ->
                    val batch = database.batch()
                    snapshot.documents.forEach { document ->
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener { error ->
                            Log.e("MoviesLocalRepository", "Error clearRemoteKeys: $error")
                            continuation.resumeWithException(error)
                        }
                }
                .addOnFailureListener { error ->
                    Log.e("MoviesLocalRepository", "Error clearRemoteKeys: $error")
                    continuation.resumeWithException(error)
                }
        }
    }

    private fun getUserDocument(): DocumentReference {
        val documentPath = auth.currentUser?.uid ?: GUEST_DOCUMENT
        return database.collection(USERS_COLLECTION).document(documentPath)
    }

    private companion object {
        const val USERS_COLLECTION = "users"
        const val GUEST_DOCUMENT = "guest"
    }
}