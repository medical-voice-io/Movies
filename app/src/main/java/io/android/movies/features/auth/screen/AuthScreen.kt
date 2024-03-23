package io.android.movies.features.auth.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.android.movies.R
import io.android.movies.features.auth.screen.event.AuthEvent
import io.android.movies.navigation.Screens
import kotlinx.coroutines.launch

@Composable
internal fun AuthScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        authViewModel.event.collect { event ->
            when(event) {
                is AuthEvent.ShowMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }
                is AuthEvent.OpenMoviesScreen -> {
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
                text = stringResource(id = R.string.auth_title),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.email,
                label = {
                    Text(
                        text = stringResource(id = R.string.auth_email)
                    )
                },
                onValueChange = authViewModel::onEmailChanged,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.password,
                label = {
                    Text(
                        text = stringResource(id = R.string.auth_password)
                    )
                },
                onValueChange = authViewModel::onPasswordChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                onClick = authViewModel::onLoginClicked,
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
                        text = stringResource(id = R.string.auth_login)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    navController.popBackStack(Screens.Auth.route, true)
                    navController.navigate(Screens.Reg.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.auth_registration)
                )
            }
        }
    }
}

@Preview
@Composable
fun AuthScreen_Preview() {
    // val navController = rememberNavController()
    // AuthScreen(navController)
}