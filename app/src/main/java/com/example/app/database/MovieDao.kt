package com.example.app.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Dao
import com.example.app.model.Movie
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    fun insertMovie(movieEntity: MovieEntity)

    @Delete
    fun deleteMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    fun getMovieById(movieId: String): MovieEntity
}