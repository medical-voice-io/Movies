package io.android.movies.features.movies.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.android.movies.features.movies.screen.listeners.FavoriteListener
import io.android.movies.features.movies.screen.models.MovieUi

@Composable
internal fun MovieVerticalComponent(
    movie: MovieUi,
    favoriteListener: FavoriteListener,
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp,
                        )
                    )
                    .fillMaxWidth()
                    .height(250.dp)
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors = listOf(
                            Color.White,
                            Color.Transparent,
                        )
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = colors,
                                startY = 150.dp.toPx()
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    },
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = movie.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = movie.year.toString(),
                    fontSize = 12.sp,
                )
                Text(
                    text = movie.rating.toString(),
                    fontSize = 12.sp,
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clickable {
                    favoriteListener.onChange(movie)
                }
        ) {
            val color = if (movie.isFavorite) {
                Color.Red
            } else {
                Color.White
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                tint = color,
                contentDescription = null,
            )
            if (!movie.isFavorite) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    tint = Color.Gray,
                    contentDescription = null,
                )
            }
        }
    }
}