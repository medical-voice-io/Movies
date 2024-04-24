package io.android.movies.features.detail.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDataResponse(
    @SerialName("total")
    val total: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("items")
    val items: List<ReviewItemResponse>,
)