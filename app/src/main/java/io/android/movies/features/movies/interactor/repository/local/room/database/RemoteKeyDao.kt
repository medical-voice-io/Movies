package io.android.movies.features.movies.interactor.repository.local.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.android.movies.features.movies.interactor.repository.local.room.entity.REMOTE_KEYS_TABLE_NAME
import io.android.movies.features.movies.interactor.repository.local.room.entity.RemoteKeyEntity

@Dao
internal interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM $REMOTE_KEYS_TABLE_NAME WHERE movie_id = :moviesId")
    suspend fun getRemoteKey(moviesId: Int): RemoteKeyEntity?

    @Query("DELETE FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun deleteRemoteKey()

    @Query("SELECT created_at FROM $REMOTE_KEYS_TABLE_NAME ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}