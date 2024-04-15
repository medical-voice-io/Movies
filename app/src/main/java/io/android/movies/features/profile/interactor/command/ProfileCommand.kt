package io.android.movies.features.profile.interactor.command

internal sealed interface  ProfileCommand {

  data class SetNicknameCommand(val nickname: String) : ProfileCommand
  
  data object LogOutCommand : ProfileCommand
}