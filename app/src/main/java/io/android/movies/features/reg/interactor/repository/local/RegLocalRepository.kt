package io.android.movies.features.reg.interactor.repository.local

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class RegLocalRepository @Inject constructor(
    private val auth: FirebaseAuth,
) {

    private val _resultRegistration = MutableSharedFlow<Result<Unit>>()
    val resultRegistration: SharedFlow<Result<Unit>> = _resultRegistration.asSharedFlow()

    suspend fun registration(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            runCatching {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        continuation.resume(Result.success(Unit))
                    }
                    .addOnFailureListener { error ->
                        continuation.resume(Result.failure(error))
                    }
            }.onFailure { error ->
                continuation.resume(Result.failure(error))
            }
        }
    }

    suspend fun saveRegResult(result: Result<Unit>) {
        _resultRegistration.emit(result)
    }
}