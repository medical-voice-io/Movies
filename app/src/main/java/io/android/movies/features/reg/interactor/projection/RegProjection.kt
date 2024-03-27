package io.android.movies.features.reg.interactor.projection

import io.android.movies.features.reg.interactor.repository.local.RegLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RegProjection @Inject constructor(
    private val regLocalRepository: RegLocalRepository,
) {

    /** Подписаться на результат регистрации */
    fun observeResultRegistration(): Flow<Result<Unit>> = regLocalRepository.resultRegistration
}