package io.android.movies.auth.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.auth.interactor.AuthAggregate
import io.android.movies.auth.interactor.AuthProjection
import io.android.movies.auth.interactor.command.AuthCommand
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
internal class AuthViewModel @Inject constructor(
    authProjection: AuthProjection,
    private val authAggregate: AuthAggregate,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event: SharedFlow<String> = _event.asSharedFlow()

    init {
        authProjection.observeResultAuthentication()
            .onEach { result ->
                result
                    .onSuccess {
                        // TODO: переход на экран фильмов
                    }
                    .onFailure {
                        // TODO: отобразить ошибку
                        _event.emit(
                            "Ошибка авторизации: ${it.message}"
                        )
                    }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Сохраняет ввод пароля в состояние экрана
     * @param email Введенный email
     */
    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    /**
     * Сохраняет ввод пароля в состояние экрана
     * @param password Введенный пароль
     */
    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    /**
     * Обработка нажатия на кнопку "Войти"
     */
    fun onLoginClicked() {
        val (email, password) = state.value
        viewModelScope.launch {
            authAggregate.handleCommand(
                AuthCommand.Login(
                    email = email,
                    password = password
                )
            )
        }
    }
}