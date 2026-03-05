package com.example.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")

data class MovieEntity(
    @PrimaryKey val movie_id: Int,
    @ColumnInfo(name = "movie_name") val movieName: String,
    @ColumnInfo(name = "movie_author") val movieAuthor: String,
    @ColumnInfo(name = "movie_price") val moviePrice: String,
    @ColumnInfo(name = "movie_rating") val movieRating: String,
    @ColumnInfo(name = "movie_desc") val movieDesc: String,
    @ColumnInfo(name = "movie_image") val movieImage: String
)
