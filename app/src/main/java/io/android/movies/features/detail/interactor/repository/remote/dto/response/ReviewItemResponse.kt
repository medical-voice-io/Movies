package io.android.movies.features.detail.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewItemResponse(
    @SerialName("kinopoiskId")
    val id: Int,
    @SerialName("positiveRating")
    val positiveRating: Int?,
    @SerialName("negativeRating")
    val negativeRating: Int?,
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
)
