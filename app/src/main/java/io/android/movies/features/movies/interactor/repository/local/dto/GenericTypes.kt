package io.android.movies.features.movies.interactor.repository.local.dto

import com.google.firebase.database.GenericTypeIndicator
import io.android.movies.features.movies.interactor.domain.write.MoviePreview

internal class RemoteKeysType : GenericTypeIndicator<List<RemoteKey>>()

internal class MoviesType : GenericTypeIndicator<List<MoviePreview>>()
