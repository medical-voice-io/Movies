package io.android.movies.features.reg.interactor.aggregate.command

internal sealed interface RegCommand {

    data class Registration(
        val email: String,
        val password: String,
    ) : RegCommand
}