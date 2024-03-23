package io.android.movies.features.reg.screen

import androidx.lifecycle.ViewModel
import io.android.movies.features.auth.screen.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


internal class RegViewModel @Inject constructor(
) : ViewModel() {

  private val _state = MutableStateFlow(RegState())
  val state: StateFlow<RegState> = _state.asStateFlow()

  fun onEmailChanged(email: String) {
    _state.value = _state.value.copy(email = email)
  }

  fun onPasswordChanged(password: String) {
    _state.value = _state.value.copy(password = password)
  }

  fun onPasswordConfirmChanged(passwordConfirm: String) {
    _state.value = _state.value.copy(passwordConfirm = passwordConfirm)
  }

  fun onRegisterClicked() {

  }

}
