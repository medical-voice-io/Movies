package io.android.movies.features.detail.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDataResponse(
    @SerialName("total")
    val total: Int,
    @SerialName("items")
    val items: List<VideoItemResponse>,
)
