package io.android.movies.features.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.android.movies.R
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
        // TODO(AndrewVorotyntsev): в строки
        Text(
//        text = stringResource(id = R.string.profile_title),
          text= "Профиль",
          fontSize = 24.sp
        )
      }

      Spacer(modifier = Modifier.height(20.dp))
      TextField(
        value = state.nickname,
        label = {
          // TODO(AndrewVorotyntsev): в строки
          Text(
             text= "Никнейм"
//            text = stringResource(id = R.string.auth_email)
          )
        },
        onValueChange = profileViewModel::onNicknameChanged,
//        enabled = !state.isLoading,
        modifier = Modifier
          .fillMaxWidth()
      )
      FilledTonalButton(onClick = profileViewModel::setNickname) {
        Text(
          text = "Сохранить имя"
        )
      }
      Spacer(modifier = Modifier.height(16.dp))

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