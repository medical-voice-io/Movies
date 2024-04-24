package io.android.movies.features.detail.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoItemResponse(
    @SerialName("url")
    val url: String,
    @SerialName("name")
    val name: String,
    @SerialName("site")
    val site: String,
)
