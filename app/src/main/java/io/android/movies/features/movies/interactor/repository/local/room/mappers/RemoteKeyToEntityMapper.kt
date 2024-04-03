package io.android.movies.features.movies.interactor.repository.local.room.mappers

import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.room.entity.RemoteKeyEntity
import javax.inject.Inject

internal class RemoteKeyToEntityMapper @Inject constructor(
) : (RemoteKey) -> RemoteKeyEntity {

    override fun invoke(remoteKey: RemoteKey): RemoteKeyEntity = RemoteKeyEntity(
        movieId = remoteKey.moviesId,
        prevPage = remoteKey.prevKey,
        currentPage = remoteKey.currentPage,
        nextPage = remoteKey.nextKey,
    )
}