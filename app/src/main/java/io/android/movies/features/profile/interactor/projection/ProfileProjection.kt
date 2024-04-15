package io.android.movies.features.profile.interactor.projection

import io.android.movies.features.profile.interactor.projector.ProfileProjector
import javax.inject.Inject


internal class ProfileProjection @Inject constructor(
  private val projector: ProfileProjector
) {

  fun getNickname(): String? {
    return projector.getNickname()
  }
}