package io.android.movies.features.movies.interactor.repository.local.firebase.extensions

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listener = addSnapshotListener { value, error ->
        when {
            error != null -> { close(error) }
            value != null -> { trySend(value) }
        }
    }
    awaitClose { listener.remove() }
}