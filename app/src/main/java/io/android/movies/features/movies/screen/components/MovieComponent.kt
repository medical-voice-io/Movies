package io.android.movies.features.movies.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.android.movies.features.movies.screen.models.MovieUi

/**
 * Карточка фильма
 */
@Composable
internal fun MovieComponent(
    modifier: Modifier = Modifier,
    movie: MovieUi
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            NameMovieComponent(
                name = movie.name,
            )
            Text(
                text = movie.year.toString()
            )
            Text(
                text = movie.rating.toString()
            )
        }
    }
}

@Composable
internal fun NameMovieComponent(
    name: String,
) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Preview
@Composable
internal fun MovieComponent_Preview() {
    Surface {
        MovieComponent(
            movie = MovieUi(
                id = 0,
                name = "Movie name",
                rating = 7.5,
                year = 2021,
                posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/301.jpg",
                isFavorite = false
            )
        )
    }
}
