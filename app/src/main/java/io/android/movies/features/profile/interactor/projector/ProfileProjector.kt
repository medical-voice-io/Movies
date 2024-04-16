package io.android.movies.features.profile.interactor.projector

import android.net.Uri
import io.android.movies.features.profile.interactor.repository.ProfileLocalRepository
import javax.inject.Inject

internal class ProfileProjector @Inject constructor(
  private val localRepository: ProfileLocalRepository,
) {

  fun logoutUser() {
    localRepository.logout();
  }

  fun setNickname(nickname: String) {
    localRepository.setNickname(nickname)
  }

  fun getNickname(): String? {
    return localRepository.getNickname()
  }

  fun getAvatar(): Uri? {
    return  localRepository.getAvatar()
  }

  fun  setAvatar(avatar: Uri?) {
    localRepository.setAvatar(avatar)
  }


}