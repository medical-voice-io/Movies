package io.android.movies.features.movies.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoviesScreen() {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            Text("Movies Screen")
        }
    }
}