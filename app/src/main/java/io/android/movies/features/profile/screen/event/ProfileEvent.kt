package io.android.movies.features.profile.screen.event

internal sealed interface  ProfileEvent {

  data object LogOutEvent : ProfileEvent
}