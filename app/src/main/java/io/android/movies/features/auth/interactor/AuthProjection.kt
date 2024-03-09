package io.android.movies.features.auth.interactor

import com.google.firebase.auth.FirebaseUser
import io.android.movies.features.auth.interactor.repository.AuthLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthProjection @Inject constructor(
    private val authLocalRepository: AuthLocalRepository,
) {

    /** Получить текущего пользователя */
    fun observeCurrentUser(): Flow<FirebaseUser?> = authLocalRepository.currentUser

    /** Подписаться на результат авторизации */
    fun observeResultAuthentication(): Flow<Result<Unit>> = authLocalRepository.resultAuthentication
}