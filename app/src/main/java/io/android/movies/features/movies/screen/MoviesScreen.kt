package io.android.movies.features.movies.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import io.android.movies.R
import io.android.movies.features.movies.screen.components.MovieComponent
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.flow.Flow

@Composable
internal fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            // TODO: Добавить строку поиска
            MoviesContentState(
                moviesFlow = viewModel.moviesFlow
            )
        }
    }
}

/**
 * Состояние загрузки экрана
 */
@Composable
internal fun MoviesLoadingState() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

/**
 * Состояние ошибки экрана
 * @param onRefreshClicked Функция обновления
 */
@Composable
internal fun MoviesErrorState(
    onRefreshClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.movies_refresh_text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = onRefreshClicked,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.movies_refresh_text)
            )
        }
    }
}

/**
 * Состояние отображения списка фильмов
 */
@Composable
internal fun MoviesContentState(
    moviesFlow: Flow<PagingData<MovieUi>>
) {
    val movies = moviesFlow.collectAsLazyPagingItems()

    // TODO: обучный список
    LazyColumn {

        items(
            count = movies.itemCount
        ) { index ->
            movies[index]?.let { movie ->
                MovieComponent(movie = movie)
            }
        }

        val loadState = movies.loadState.mediator
        item {
            if (loadState?.refresh is LoadState.Loading) {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = stringResource(id = R.string.movies_refresh)
                    )

                    CircularProgressIndicator()
                }
            }

            if (loadState?.append is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError = (loadState.append is LoadState.Error) || movies.itemCount > 1
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillParentMaxSize()
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Rounded.Warning, contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = error.message ?: error.toString(),
                        textAlign = TextAlign.Center,
                    )

                    Button(
                        onClick = {
                            movies.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        }
                    )
                }
            }
        }
    }
}
