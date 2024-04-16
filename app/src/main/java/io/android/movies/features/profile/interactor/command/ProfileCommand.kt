package io.android.movies.features.profile.interactor.command

import android.net.Uri

internal sealed interface  ProfileCommand {

  data class SetNicknameCommand(val nickname: String) : ProfileCommand

  data class SetAvatarCommand(val avatar: Uri?) : ProfileCommand

  data object LogOutCommand : ProfileCommand
}