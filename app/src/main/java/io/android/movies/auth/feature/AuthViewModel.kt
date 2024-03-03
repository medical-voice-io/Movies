package io.android.movies.auth.feature

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

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
}