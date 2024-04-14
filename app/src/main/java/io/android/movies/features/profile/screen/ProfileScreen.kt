package io.android.movies.features.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.android.movies.features.profile.screen.event.ProfileEvent
import io.android.movies.navigation.Screens

@Composable
internal fun ProfileScreen(
  navController: NavController,
  profileViewModel: ProfileViewModel = hiltViewModel()
) {
  val state by profileViewModel.state.collectAsState()

  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    profileViewModel.event.collect { event ->
      when(event) {
        is ProfileEvent.LogOutEvent -> {
          navController.navigate(Screens.Auth.route)
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

      FilledTonalButton(onClick = profileViewModel::logOut) {
        Text(
          text = "Выйти"
        )
      }

      // TODO: добавить поля с именем и почтой
      // TODO:добавить загрузку аватарки
    }
  }
}

@Preview
@Composable
fun ProfileScreen_Preview() {
  // val navController = rememberNavController()
  // ProfileScreen(navController)
}