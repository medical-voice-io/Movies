package io.android.movies.features.reg.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.android.movies.R
import io.android.movies.features.reg.screen.event.RegEvent
import io.android.movies.navigation.Screens
import kotlinx.coroutines.launch

@Composable
internal fun RegScreen(
    navController: NavController,
    regViewModel: RegViewModel = hiltViewModel()
) {
    val state by regViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val content = LocalContext.current

    LaunchedEffect(Unit) {
        regViewModel.event.collect { event ->
            when(event) {
                is RegEvent.ShowMessageRes -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = content.getString(event.messageRes),
                        )
                    }
                }
                is RegEvent.ShowMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }
                is RegEvent.OpenMoviesScreen -> {
                    navController.popBackStack(Screens.Reg.route, true)
                    navController.navigate(Screens.Movies.route)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.reg_title),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.email,
                label = {
                    Text(
                        text = stringResource(id = R.string.reg_email)
                    )
                },
                onValueChange = regViewModel::onEmailChanged,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.password,
                label = {
                    Text(
                        text = stringResource(id = R.string.reg_password)
                    )
                },
                onValueChange = regViewModel::onPasswordChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.passwordConfirm,
                label = {
                    Text(
                        text = stringResource(id = R.string.reg_repeat_password)
                    )
                },
                onValueChange = regViewModel::onPasswordConfirmChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                onClick = regViewModel::onRegisterClicked,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .then(Modifier.size(16.dp))
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.auth_registration)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    navController.popBackStack(Screens.Reg.route, true)
                    navController.navigate(Screens.Auth.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.auth_login)
                )
            }
        }
    }
}