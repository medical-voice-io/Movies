package io.android.movies.features.profile.screen

import android.net.Uri

internal data class ProfileState(
  val nickname: String = "",
  val avatar: Uri? = null
)