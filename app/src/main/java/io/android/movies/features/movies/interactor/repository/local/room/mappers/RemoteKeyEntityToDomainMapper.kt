package io.android.movies.features.movies.interactor.repository.local.room.mappers

import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.room.entity.RemoteKeyEntity
import javax.inject.Inject

internal class RemoteKeyEntityToDomainMapper @Inject constructor(
) : (RemoteKeyEntity) -> RemoteKey {
    override fun invoke(entity: RemoteKeyEntity): RemoteKey = RemoteKey(
        moviesId = entity.movieId,
        prevKey = entity.prevPage,
        currentPage = entity.currentPage,
        nextKey = entity.nextPage,
    )
}