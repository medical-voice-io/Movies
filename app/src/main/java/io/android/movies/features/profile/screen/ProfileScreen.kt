package io.android.movies.features.profile.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.android.movies.R
import io.android.movies.features.profile.screen.event.ProfileEvent
import io.android.movies.navigation.Screens

@Composable
internal fun ProfileScreen(
  navController: NavController,
  profileViewModel: ProfileViewModel = hiltViewModel()
) {
  val state by profileViewModel.state.collectAsState()

  val galleryLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { image ->
      if (image != null) {
        profileViewModel.setAvatar(image)
      }
    }

  LaunchedEffect(Unit) {
    profileViewModel.event.collect { event ->
      when (event) {
        is ProfileEvent.LogOutEvent -> {
          navController.navigate(Screens.Auth.route)
        }
      }
    }
  }

  Scaffold(
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
          modifier = Modifier.align(Alignment.CenterVertically),
          text = stringResource(id = R.string.profile_title),
          fontSize = 24.sp
        )
      }
      Spacer(modifier = Modifier.height(20.dp))
      if (state.avatar != null) {
        AsyncImage(
          model = state.avatar,
          contentDescription = null,
          modifier = Modifier
            .clip(
              RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 20.dp,
                bottomStart = 20.dp,
              )
            )
            .height(200.dp)
            .width(200.dp)
            .align(Alignment.CenterHorizontally)
            .graphicsLayer { alpha = 0.99f }
            .pointerInput(Unit) {
              detectTapGestures {
                galleryLauncher.launch("image/*")
              }
            },
          contentScale = ContentScale.Crop,
        )
      }
      Spacer(modifier = Modifier.height(10.dp))
      FilledTonalButton(onClick = {
        galleryLauncher.launch("image/*")

      }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Text(
          text = stringResource(id = R.string.load_avatar)
        )
      }
      Spacer(modifier = Modifier.height(20.dp))
      OutlinedTextField(
        value = state.nickname,
        label = {
          Text(
            text = stringResource(id = R.string.nickname)
          )
        },
        onValueChange = profileViewModel::onNicknameChanged,
        modifier = Modifier
          .fillMaxWidth()
      )
      FilledTonalButton(onClick = profileViewModel::setNickname) {
        Text(
          text = stringResource(id = R.string.set_nickname)
        )
      }
      Spacer(modifier = Modifier.height(16.dp))

      FilledTonalButton(onClick = profileViewModel::logOut) {
        Text(
          text = stringResource(id = R.string.logout)
        )
      }
    }
  }
}
