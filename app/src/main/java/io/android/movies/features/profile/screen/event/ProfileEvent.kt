package io.android.movies.features.profile.screen.event

import android.net.Uri

/** События экрана профиля */
internal sealed interface  ProfileEvent {

  /** События логаута */
  data object LogOutEvent : ProfileEvent
}