package io.android.movies.features.profile.screen

import io.android.movies.features.auth.screen.AuthState


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.auth.screen.event.AuthEvent
import io.android.movies.features.auth.interactor.AuthAggregate
import io.android.movies.features.auth.interactor.AuthProjection
import io.android.movies.features.auth.interactor.command.AuthCommand
import io.android.movies.features.profile.screen.event.ProfileEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel экрана авторизации
 */
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
//  authProjection: ProfileProjection,
//  private val authAggregate: ProfileAggregate,
) : ViewModel() {

  private val _state = MutableStateFlow(ProfileState())
  val state: StateFlow<ProfileState> = _state.asStateFlow()

  private val _event = MutableSharedFlow<ProfileEvent>()
  val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

//  init {
//    authProjection.observeResultAuthentication()
//      .onEach { result ->
//        result
//          .onSuccess {
//            _event.emit(AuthEvent.OpenMoviesScreen)
//          }
//          .onFailure {
//            _event.emit(
//              AuthEvent.ShowMessage(
//                message = "Ошибка авторизации: ${it.message}"
//              )
//            )
//          }
//        updateLoading(isLoading = false)
//      }
//      .launchIn(viewModelScope)
//  }

}