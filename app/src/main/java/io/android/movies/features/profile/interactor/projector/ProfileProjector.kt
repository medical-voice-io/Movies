package io.android.movies.features.profile.interactor.projector

import io.android.movies.features.profile.interactor.repository.ProfileLocalRepository
import javax.inject.Inject

internal class ProfileProjector @Inject constructor(
  private val localRepository: ProfileLocalRepository,
) {

  fun logoutUser() {
    localRepository.logout();
  }
}