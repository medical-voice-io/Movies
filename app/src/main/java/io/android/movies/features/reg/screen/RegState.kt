package io.android.movies.features.reg.screen

internal data class RegState(
  val email: String = "",
  val password: String = "",
  val passwordConfirm: String = "",
  val isLoading: Boolean = false,
)