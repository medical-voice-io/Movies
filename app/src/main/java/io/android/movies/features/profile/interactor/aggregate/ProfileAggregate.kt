package io.android.movies.features.profile.interactor.aggregate

import io.android.movies.features.profile.interactor.command.ProfileCommand
import io.android.movies.features.profile.interactor.projector.ProfileProjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ProfileAggregate @Inject constructor(
  private val profileProjector: ProfileProjector,
) {

  /**
   * Обрабатывает комманду
   */
  suspend fun handleCommand(command: ProfileCommand) = when(command) {
    is ProfileCommand.LogOutCommand -> logoutUser()
  }

  private suspend fun logoutUser() =
    withContext(Dispatchers.IO) {
      profileProjector.logoutUser()
    }
}