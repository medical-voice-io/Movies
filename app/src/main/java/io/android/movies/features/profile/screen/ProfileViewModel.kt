package io.android.movies.features.profile.screen


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.profile.interactor.aggregate.ProfileAggregate
import io.android.movies.features.profile.interactor.command.ProfileCommand
import io.android.movies.features.profile.interactor.projection.ProfileProjection
import io.android.movies.features.profile.screen.event.ProfileEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel экрана авторизации
 */
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
  private val profileProjection: ProfileProjection,
  private val profileAggregate: ProfileAggregate,
) : ViewModel() {

  private val _state = MutableStateFlow(ProfileState())
  val state: StateFlow<ProfileState> = _state.asStateFlow()

  private val _event = MutableSharedFlow<ProfileEvent>()
  val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

   init {
    fetchUserData()
  }

  private fun fetchUserData() {
    profileProjection.getNickname()?.let {
      _state.value = _state.value.copy(nickname = it)
    }
    // TODO: Получать фото
    profileProjection.getAvatar()?.let {
      _state.value = _state.value.copy(avatar = it)
    }
  }

  fun onNicknameChanged(nickname: String) {
    _state.value = _state.value.copy(nickname = nickname)
  }


  fun setNickname() {
    viewModelScope.launch {
      profileAggregate.handleCommand(ProfileCommand.SetNicknameCommand(_state.value.nickname))
    }
  }

  fun setAvatar(avatar: Uri?) {
    _state.value = _state.value.copy(avatar = avatar)
    viewModelScope.launch {
      profileAggregate.handleCommand(ProfileCommand.SetAvatarCommand(avatar))
    }
  }

   fun logOut() {
    FirebaseAuth.getInstance().signOut();
    viewModelScope.launch {
      profileAggregate.handleCommand(ProfileCommand.LogOutCommand)
      _event.emit(ProfileEvent.LogOutEvent)
    }
  }

}