package io.android.movies.features.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import io.android.movies.features.auth.screen.AuthViewModel
import io.android.movies.features.auth.screen.event.AuthEvent
import io.android.movies.navigation.Screens
import kotlinx.coroutines.launch

@Composable
internal fun ProfileScreen(
  navController: NavController,
  profileViewModel: ProfileViewModel = hiltViewModel()
) {
  val state by profileViewModel.state.collectAsState()

  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

//  LaunchedEffect(Unit) {
//    profileViewModel.event.collect { event ->
//      when(event) {
//        is AuthEvent.ShowMessage -> {
//          scope.launch {
//            snackbarHostState.showSnackbar(
//              message = event.message,
//            )
//          }
//        }
//        is AuthEvent.OpenMoviesScreen -> {
//          navController.navigate(Screens.Movies.route)
//        }
//      }
//    }
//  }

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
      Row {
        IconButton(
          onClick = {
            navController.popBackStack()
          }
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
          )
        }
        Text(
//        text = stringResource(id = R.string.profile_title),
          text= "Профиль",
          fontSize = 24.sp
        )
      }

      // TODO: добавить поля с именем и почтой
      // TODO:добавить загрузку аватарки
      // TODO: добавить кнопку логаута
    }
  }
}

@Preview
@Composable
fun ProfileScreen_Preview() {
  // val navController = rememberNavController()
  // ProfileScreen(navController)
}