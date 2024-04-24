package io.android.movies.features.detail.screen

import android.app.Activity
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dagger.hilt.android.EntryPointAccessors
import io.android.movies.MainActivity
import io.android.movies.R
import io.android.movies.features.detail.interactor.domain.write.MovieDetails
import io.android.movies.features.detail.interactor.domain.write.MovieReview
import io.android.movies.features.detail.interactor.domain.write.MovieVideo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
    navController: NavController,
    id: Int,
) {
    val viewModelFactory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    )

    val viewModel: DetailViewModel = viewModel(
        factory = DetailViewModel.provideFactory(viewModelFactory.detailViewModelFactory(), id)
    )
    LaunchedEffect(Unit) {
        viewModel.screenLoaded()
    }
    val state by viewModel.movieState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(state.title ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (state) {
                is MovieState.Loading ->
                    item {
                        LoadingMovie()
                    }

                is MovieState.Loaded -> LoadedMovie(state as MovieState.Loaded)
            }
        }
    }
}

@Composable
private fun LoadingMovie() {
    CircularProgressIndicator()
}

private fun LazyListScope.LoadedMovie(
    state: MovieState.Loaded,
) {
    item {
        MovieDetails(state.details)
    }
    item {
        MovieVideos(state.videos)
    }
    MovieReviews(state.reviews)
}

@Composable
private fun MovieDetails(
    details: MovieDetails,
) {
    AsyncImage(
        model = details.posterUrl,
        contentDescription = stringResource(R.string.film_poster_description),
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                )
            )
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Fit,
    )
    details.description?.also { desc ->
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = desc
        )
    }
    details.rating?.also { rating ->
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.film_details_rating, rating),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun MovieVideos(
    videos: List<String>,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(videos) { videoId ->
            YoutubePlayer(
                modifier = Modifier
                    .width(250.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                youtubeVideoId = videoId,
                lifecycleOwner = LocalLifecycleOwner.current,
            )
        }
    }
}

private fun LazyListScope.MovieReviews(
    reviews: List<MovieReview>,
) {
    items(reviews) { review ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
                .padding(8.dp),
        ) {
            review.title?.let { title ->
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                )
            }
            review.description?.let { description ->
                Text(
                    text = description.annotatedString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            }
            review.author?.let { author ->
                Text(
                    text = author,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

private val MovieState.title: String?
    get() = when (this) {
        is MovieState.Loaded -> this.details.name
        else -> null
    }

fun String.annotatedString(): AnnotatedString {
    val spannableString = SpannableStringBuilder(this).toString()
    val spanned = HtmlCompat.fromHtml(spannableString, HtmlCompat.FROM_HTML_MODE_COMPACT)
    return spanned.annotatedString()
}

fun Spanned.annotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@annotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    ), start, end
                )
            }

            is UnderlineSpan -> addStyle(
                SpanStyle(textDecoration = TextDecoration.Underline),
                start,
                end
            )

            is ForegroundColorSpan -> addStyle(
                SpanStyle(color = Color(span.foregroundColor)),
                start,
                end
            )
        }
    }
}