package io.android.movies.features.splash.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.auth.interactor.AuthProjection
import io.android.movies.features.splash.screen.event.SplashEvent
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.system.measureTimeMillis

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val authProjection: AuthProjection,
) : ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val timer = viewModelScope.launch { startSplashTimer() }
            val user = getCurrentUser()
            timer.join()

            val nextScreenEvent = SplashEvent.OpenMoviesScreen
                .takeIf { user != null }
                ?: SplashEvent.OpenAuthScreen
            _event.emit(nextScreenEvent)
        }
    }

    private suspend fun startSplashTimer(
        time: Long = 3 * 1000L
    ) {
        delay(time)
    }

    private suspend fun getCurrentUser(): FirebaseUser? =
        withContext(viewModelScope.coroutineContext) {
            authProjection.getCurrentUser()
        }
}