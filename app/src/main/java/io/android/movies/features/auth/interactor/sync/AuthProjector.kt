package io.android.movies.features.auth.interactor.sync

import io.android.movies.features.auth.interactor.domain.write.UserLogin
import io.android.movies.features.auth.interactor.repository.AuthLocalRepository
import io.android.movies.features.auth.interactor.repository.AuthRemoteRepository
import javax.inject.Inject

/**
 * Проецирует модель записи в модель чтения
 */
internal class AuthProjector @Inject constructor(
    private val localRepository: AuthLocalRepository,
    private val remoteRepository: AuthRemoteRepository,
) {

    /**
     * Проецирует модель записи в модель чтения
     * @param userLogin Модель записи
     */
    suspend fun project(userLogin: UserLogin) {
        val result = localRepository.signIn(
            email = userLogin.email,
            password = userLogin.password,
        )
        localRepository.saveAuthResult(result)
    }
}