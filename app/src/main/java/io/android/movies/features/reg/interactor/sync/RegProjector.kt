package io.android.movies.features.reg.interactor.sync

import io.android.movies.features.reg.interactor.domain.write.UserLogin
import io.android.movies.features.reg.interactor.repository.local.RegLocalRepository
import javax.inject.Inject

internal class RegProjector @Inject constructor(
    private val localRepository: RegLocalRepository,
) {

    suspend fun project(userLogin: UserLogin) {
        val result = localRepository.registration(
            email = userLogin.email,
            password = userLogin.password,
        )
        localRepository.saveRegResult(result)
    }
}