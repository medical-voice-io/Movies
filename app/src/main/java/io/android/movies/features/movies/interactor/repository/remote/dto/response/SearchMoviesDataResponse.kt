package io.android.movies.features.movies.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SearchMoviesDataResponse(
    @SerialName("films")
    val movies: List<SearchMovieResponse>,
)

@Serializable
internal class SearchMovieResponse(
    @SerialName("filmId")
    val id: Int,
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("nameEn")
    val nameEn: String?,
    @SerialName("rating")
    val rating: String,
    @SerialName("year")
    val year: String,
    @SerialName("posterUrlPreview")
    val posterUrlPreview: String,
)