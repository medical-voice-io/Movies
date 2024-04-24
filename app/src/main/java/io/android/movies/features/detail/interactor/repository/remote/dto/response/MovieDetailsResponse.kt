package io.android.movies.features.detail.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDetailsResponse(
    @SerialName("kinopoiskId")
    val id: Int,
    @SerialName("nameRu")
    val nameRu: String? = null,
    @SerialName("nameOriginal")
    val nameOriginal: String? = null,
    @SerialName("nameEn")
    val nameEn: String? = null,
    @SerialName("ratingKinopoisk")
    val rating: Float? = null,
    @SerialName("year")
    val year: Int? = null,
    @SerialName("posterUrl")
    val posterUrl: String? = null,
    @SerialName("reviewCounts")
    val reviewCounts: Int? = null,
    @SerialName("slogan")
    val slogan: String? = null,
    @SerialName("description")
    val description: String? = null,
)
