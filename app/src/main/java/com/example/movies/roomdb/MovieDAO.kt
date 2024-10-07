package com.example.movies.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.model.Movie

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movie: Movie) : List<Long>

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies() : List<Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId : Int) : Movie

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()

}