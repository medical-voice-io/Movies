package io.android.movies.features.detail.interactor.repository.local.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.getValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

internal class MovieDatabaseRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) : MovieLocalRepository {
    override fun getMovieDetails(id: Int): Flow<MovieDetails> = callbackFlow {
        val postListener = getReference(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getValue<MovieDetails>()?.also { movie ->
                    this@callbackFlow.trySend(movie)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // No-op
            }
        })
        awaitClose {
            getReference(id).removeEventListener(postListener)
        }
    }

    override fun getMovieReviews(id: Int): Flow<List<MovieReview>> = callbackFlow {
        val postListener =
            getReviewReference(id).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val reviews = dataSnapshot.children.mapNotNull { it.getValue<MovieReview>() }
                    this@callbackFlow.trySend(reviews)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // No-op
                }
            })
        awaitClose {
            getReference(id).removeEventListener(postListener)
        }
    }

    override fun getMovieVideos(id: Int): Flow<List<MovieVideo>> = callbackFlow {
        val postListener = getVideoReference(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val reviews = dataSnapshot.children.mapNotNull { it.getValue<MovieVideo>() }
                this@callbackFlow.trySend(reviews)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // No-op
            }
        })
        awaitClose {
            getReference(id).removeEventListener(postListener)
        }
    }

    override suspend fun saveDetails(movie: MovieDetails) {
        withContext(Dispatchers.IO) {
            getReference(movie.id).setValue(movie)
        }
    }

    override suspend fun saveVideos(id: Int, videos: List<MovieVideo>) {
        withContext(Dispatchers.IO) {
            val ref = getVideoReference(id)
            for (video in videos) {
                video.url?.also { url ->
                    ref.child(url).setValue(video)
                }
            }
        }
    }

    override suspend fun saveReviews(id: Int, reviews: List<MovieReview>) {
        withContext(Dispatchers.IO) {
            val ref = getReviewReference(id)
            for (review in reviews) {
                ref.child(review.id.toString()).setValue(review)
            }
        }
    }

    private fun getReference(id: Int): DatabaseReference = database.getReference(
        "users/${auth.currentUser?.uid}/details/$id"
    )

    private fun getVideoReference(id: Int): DatabaseReference = database.getReference(
        "users/${auth.currentUser?.uid}/video/$id"
    )

    private fun getReviewReference(id: Int): DatabaseReference = database.getReference(
        "users/${auth.currentUser?.uid}/review/$id"
    )
}