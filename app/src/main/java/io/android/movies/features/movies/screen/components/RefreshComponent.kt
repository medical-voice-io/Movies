package io.android.movies.features.movies.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.android.movies.R

@Composable
internal fun RefreshComponent(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.movies_refresh),
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            modifier = Modifier
                .then(Modifier.size(16.dp))
        )
    }
}