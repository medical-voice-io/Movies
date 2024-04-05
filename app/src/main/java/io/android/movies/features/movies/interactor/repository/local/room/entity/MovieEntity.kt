package io.android.movies.features.movies.interactor.repository.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val MOVIE_TABLE_NAME = "movie"

@Entity(tableName = MOVIE_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    @ColumnInfo(name = "page")
    val page: Int,
)