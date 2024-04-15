package io.android.movies.features.movies.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.android.movies.R
import io.android.movies.features.movies.screen.components.MovieVerticalComponent
import io.android.movies.features.movies.screen.listeners.MoviesListeners
import io.android.movies.features.movies.screen.models.MovieUi
import io.android.movies.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
internal fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    Scaffold { contentPadding ->

        val state by viewModel.uiState.collectAsState()
        val moviesListeners = MoviesListeners(
            favoriteListener = viewModel::onFavoriteChanged,
            searchListener = viewModel::onSearchQueryChanged,
            filterListener = viewModel::onFilterChange,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            ToolbarComponent(
                state = state,
                moviesListeners = moviesListeners,
                navController = navController,
            )
            MoviesContentState(
                moviesFlow = viewModel.moviesFlow,
                moviesListeners = moviesListeners,
            )
        }
    }
}

/**
 * Toolbar экрана списка фильмов
 */
@Composable
internal fun ToolbarComponent(
    state: MoviesState,
    moviesListeners: MoviesListeners,
    navController: NavController,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        TextField(
            value = state.searchQuery,
            onValueChange = moviesListeners.searchListener::onChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.movies_search_hint))
            },
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = {
                moviesListeners.filterListener.onChange(
                    needFilter = !state.isFilterEnabled
                )
            }
        ) {
            val tint = if (state.isFilterEnabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }

            Icon(
                imageVector = Icons.Default.Favorite,
                tint = tint,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = {
                // Переход в профиль
                navController.navigate(Screens.Profile.route)
            }
        ) {
            val tint = if (state.isFilterEnabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }

            Icon(
                imageVector = Icons.Default.Person,
                tint = tint,
                contentDescription = null,
            )
        }
    }
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
    moviesFlow: Flow<PagingData<MovieUi>>,
    moviesListeners: MoviesListeners,
) {
    val movies = moviesFlow.collectAsLazyPagingItems()

    val isLoading = movies.loadState.refresh is LoadState.Loading
    when {
        isLoading && movies.itemCount == 0 -> {
            MoviesLoadingState()
        }

        !isLoading && movies.itemCount == 0 -> {
            EmptyStateComponent(
                onRefreshClicked = movies::refresh
            )
        }

        else -> {
            MoviesListComponent(
                movies = movies,
                moviesListeners = moviesListeners
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

@Composable
fun EmptyStateComponent(
    onRefreshClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.movies_empty_text)
        )
        TextButton(
            onClick = onRefreshClicked
        ) {
            Text(
                text = stringResource(id = R.string.movies_refresh)
            )
        }
    }
}

@Composable
internal fun MoviesListComponent(
    movies: LazyPagingItems<MovieUi>,
    moviesListeners: MoviesListeners,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = movies.itemCount
        ) { index ->
            movies[index]?.let { movie ->
                MovieVerticalComponent(
                    movie = movie,
                    favoriteListener = moviesListeners.favoriteListener
                )
            }
        }

        if (movies.loadState.append is LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }

    // TODO: обучный список
    // LazyColumn {
    //
    //     items(
    //         count = movies.itemCount
    //     ) { index ->
    //         movies[index]?.let { movie ->
    //             MovieComponent(movie = movie)
    //         }
    //     }
    //
    //     val loadState = movies.loadState.mediator
    //     item {
    //         if (loadState?.refresh is LoadState.Loading) {
    //             Column(
    //                 modifier = Modifier
    //                     .fillParentMaxSize(),
    //                 horizontalAlignment = Alignment.CenterHorizontally,
    //                 verticalArrangement = Arrangement.Center,
    //             ) {
    //                 Text(
    //                     modifier = Modifier
    //                         .padding(8.dp),
    //                     text = stringResource(id = R.string.movies_refresh)
    //                 )
    //
    //                 CircularProgressIndicator()
    //             }
    //         }
    //
    //         if (loadState?.append is LoadState.Loading) {
    //             Box(
    //                 modifier = Modifier
    //                     .fillMaxWidth()
    //                     .padding(16.dp),
    //                 contentAlignment = Alignment.Center,
    //             ) {
    //                 CircularProgressIndicator()
    //             }
    //         }
    //
    //         if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
    //             val isPaginatingError = (loadState.append is LoadState.Error) || movies.itemCount > 1
    //             val error = if (loadState.append is LoadState.Error)
    //                 (loadState.append as LoadState.Error).error
    //             else
    //                 (loadState.refresh as LoadState.Error).error
    //
    //             val modifier = if (isPaginatingError) {
    //                 Modifier.padding(8.dp)
    //             } else {
    //                 Modifier.fillParentMaxSize()
    //             }
    //             Column(
    //                 modifier = modifier,
    //                 verticalArrangement = Arrangement.Center,
    //                 horizontalAlignment = Alignment.CenterHorizontally,
    //             ) {
    //                 if (!isPaginatingError) {
    //                     Icon(
    //                         modifier = Modifier
    //                             .size(64.dp),
    //                         imageVector = Icons.Rounded.Warning, contentDescription = null
    //                     )
    //                 }
    //
    //                 Text(
    //                     modifier = Modifier
    //                         .padding(8.dp),
    //                     text = error.message ?: error.toString(),
    //                     textAlign = TextAlign.Center,
    //                 )
    //
    //                 Button(
    //                     onClick = {
    //                         movies.refresh()
    //                     },
    //                     content = {
    //                         Text(text = "Refresh")
    //                     }
    //                 )
    //             }
    //         }
    //     }
    // }
}
