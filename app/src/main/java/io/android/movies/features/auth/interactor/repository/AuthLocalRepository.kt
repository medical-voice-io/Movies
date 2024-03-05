package io.android.movies.features.auth.interactor.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Репозиторий авторизации для работы с локальной БД
 */
internal interface AuthLocalRepository {

    val currentUser: FirebaseUser?

    val currentUserFLow: StateFlow<FirebaseUser?>
    val resultAuthentication: SharedFlow<Result<Unit>>

    /**
     * Авторизоваться
     * @param email Почта
     * @param password Пароль
     */
    suspend fun signIn(email: String, password: String): Result<Unit>

    /**
     * Сохраняет результат авторизации
     * @param result Результат авторизации
     */
    suspend fun saveAuthResult(result: Result<Unit>)
}

internal class AuthLocalRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthLocalRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    private val _currentUserFlow = MutableStateFlow<FirebaseUser?>(null)
    override val currentUserFLow: StateFlow<FirebaseUser?> = _currentUserFlow.asStateFlow()

    private val _resultAuthentication = MutableSharedFlow<Result<Unit>>()
    override val resultAuthentication: SharedFlow<Result<Unit>> = _resultAuthentication.asSharedFlow()

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            runCatching {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        _currentUserFlow.value = auth.currentUser
                        continuation.resume(Result.success(Unit))
                    }
                    .addOnFailureListener { error ->
                        continuation.resume(Result.failure(error))
                    }
            }
                .onFailure { error ->
                    continuation.resume(Result.failure(error))
                }
        }
    }

    override suspend fun saveAuthResult(result: Result<Unit>) {
        _resultAuthentication.emit(result)
    }
}
