package io.android.movies.features.reg.interactor.aggregate

import io.android.movies.features.reg.interactor.aggregate.command.RegCommand
import io.android.movies.features.reg.interactor.domain.write.UserLogin
import io.android.movies.features.reg.interactor.sync.RegProjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RegAggregate @Inject constructor(
    private val regProjector: RegProjector,
) {

    suspend fun handleCommand(command: RegCommand) = when(command) {
        is RegCommand.Registration -> registerUser(command)
    }

    private suspend fun registerUser(
        command: RegCommand.Registration
    ) = withContext(Dispatchers.IO) {
        val userLogin = UserLogin(
            email = command.email,
            password = command.password,
        )
        regProjector.project(userLogin)
    }
}