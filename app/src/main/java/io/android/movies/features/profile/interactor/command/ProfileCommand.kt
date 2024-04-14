package io.android.movies.features.profile.interactor.command

internal sealed interface  ProfileCommand {

  data object LogOutCommand : ProfileCommand
}