package io.android.movies.features.movies.interactor.repository.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val REMOTE_KEYS_TABLE_NAME = "remote_keys"

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
internal data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "prev_page")
    val prevPage: Int?,
    @ColumnInfo(name = "current_page")
    val currentPage: Int,
    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)