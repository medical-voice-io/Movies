package io.android.movies.features.profile.interactor.command

import android.net.Uri

/** Команды экрана профиля */
internal sealed interface ProfileCommand {

  /**
   * Команда установить никнейм
   * @property nickname Никнейм
   */
  data class SetNicknameCommand(val nickname: String) : ProfileCommand

  /**
   * Команда установить аватар
   * @property avatar Ссылка на аватар
   */
  data class SetAvatarCommand(val avatar: Uri?) : ProfileCommand

  /**
   * Команда выйти из приложения
   */
  data object LogOutCommand : ProfileCommand
}