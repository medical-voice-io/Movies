package io.android.movies.features.reg.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.R
import io.android.movies.features.reg.interactor.aggregate.RegAggregate
import io.android.movies.features.reg.interactor.aggregate.command.RegCommand
import io.android.movies.features.reg.interactor.projection.RegProjection
import io.android.movies.features.reg.screen.event.RegEvent
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

@HiltViewModel
internal class RegViewModel @Inject constructor(
    regProjection: RegProjection,
    private val regAggregate: RegAggregate,
) : ViewModel() {

    private val _state = MutableStateFlow(RegState())
    val state: StateFlow<RegState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegEvent>()
    val event: SharedFlow<RegEvent> = _event.asSharedFlow()

    init {
        regProjection.observeResultRegistration()
            .onEach { result ->
                result
                    .onSuccess {
                        _event.emit(RegEvent.OpenMoviesScreen)
                    }
                    .onFailure { error ->
                        _event.emit(
                            RegEvent.ShowMessage(
                                message = "Ошибка регистрации: ${error.message}"
                            )
                        )
                    }
                updateLoading(isLoading = false)
            }
            .launchIn(viewModelScope)
    }

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
        updateLoading(isLoading = true)
        val (email, password, confirmPassword) = _state.value
        val isValidPassword = validatePasswords(password, confirmPassword)
        viewModelScope.launch {
            if (isValidPassword) {
                regAggregate.handleCommand(
                    RegCommand.Registration(
                        email = email,
                        password = password,
                    )
                )
            } else {
                _event.emit(
                    RegEvent.ShowMessageRes(
                        messageRes = R.string.reg_invalid_password
                    )
                )
                updateLoading(isLoading = false)
            }
        }
    }

    private suspend fun handleRegResult(result: Result<Unit>) {
        result
            .onSuccess {
                _event.emit(RegEvent.OpenMoviesScreen)
            }
            .onFailure { error ->
                _event.emit(
                    RegEvent.ShowMessage(
                        message = "Ошибка регистрации: ${error.message}"
                    )
                )
            }
        updateLoading(isLoading = false)
    }

    private fun validatePasswords(
        password: String,
        confirmPassword: String,
    ): Boolean = password.trim() == confirmPassword.trim()

    private fun updateLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}
