package io.android.movies.features.detail.interactor.repository.local.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class MovieFirestoreRepository @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : MovieLocalRepository {

    override fun getMovieDetails(id: Int): Flow<MovieDetails> = callbackFlow {
        val snapshot = getDetailsDocument(id)
        val listener = snapshot.addSnapshotListener { value, error ->
            value?.toObject(MovieDetails::class.java)?.also { movie ->
                this@callbackFlow.trySend(movie)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override fun getMovieReviews(id: Int): Flow<List<MovieReview>> = callbackFlow {
        val snapshot = getReviewDocument(id)
        val listener = snapshot.addSnapshotListener { value, error ->
            value?.toObjects(MovieReview::class.java)?.also { reviews ->
                this@callbackFlow.trySend(reviews.toList())
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override fun getMovieVideos(id: Int): Flow<List<MovieVideo>> = callbackFlow {
        val snapshot = getVideoDocument(id)
        val listener = snapshot.addSnapshotListener { value, error ->
            value?.toObjects(MovieVideo::class.java)?.also { videos ->
                this@callbackFlow.trySend(videos.toList())
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun saveDetails(movie: MovieDetails) {
        withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                val detailsDocument = getDetailsDocument(movie.id)
                database.runBatch { batch ->
                    batch.set(detailsDocument, movie, SetOptions.merge())
                }
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { error ->
                        Log.e("MovieLocalRepository", "Error insetMovie: $error")
                        continuation.resumeWithException(error)
                    }
            }
        }
    }

    override suspend fun saveReviews(id: Int, reviews: List<MovieReview>) {
        withContext(Dispatchers.IO) {
            val collection = getReviewDocument(id)
            suspendCoroutine { continuation ->
                database.runBatch { batch ->
                    for (review in reviews) {
                        batch.set(
                            collection.document(review.id.toString()),
                            review,
                            SetOptions.merge()
                        )
                    }
                }
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { error ->
                        Log.e("MovieLocalRepository", "Error insetReviews: $error")
                        continuation.resumeWithException(error)
                    }
            }
        }
    }

    override suspend fun saveVideos(id: Int, videos: List<MovieVideo>) {
        withContext(Dispatchers.IO) {
            val collection = getVideoDocument(id)
            suspendCoroutine { continuation ->
                database.runBatch { batch ->
                    for (video in videos) {
                        video.url?.also { url ->
                            batch.set(
                                collection.document(url.replace("/", "_")),
                                video,
                                SetOptions.merge()
                            )
                        }
                    }
                }
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { error ->
                        Log.e("MovieLocalRepository", "Error insetVideos: $error")
                        continuation.resumeWithException(error)
                    }
            }
        }
    }

    private fun getDetailsDocument(id: Int): DocumentReference = getUserDocument()
        .collection(id.toString()).document(DETAILS_COLLECTION)

    private fun getVideoDocument(id: Int) = getUserDocument()
        .collection(id.toString()).document(VIDEOS_DOCUMENT).collection(DATA_COLLECTION)

    private fun getReviewDocument(id: Int) = getUserDocument()
        .collection(id.toString()).document(REVIEWS_DOCUMENT).collection(DATA_COLLECTION)

    private fun getUserDocument(): DocumentReference {
        val documentPath = auth.currentUser?.uid ?: GUEST_DOCUMENT
        return database.collection(USERS_DOCUMENT).document(documentPath)
    }

    private companion object {
        const val USERS_DOCUMENT = "users"
        const val REVIEWS_DOCUMENT = "reviews"
        const val VIDEOS_DOCUMENT = "videos"
        const val DETAILS_COLLECTION = "details"
        const val DATA_COLLECTION = "data"
        const val GUEST_DOCUMENT = "guest"
    }
}