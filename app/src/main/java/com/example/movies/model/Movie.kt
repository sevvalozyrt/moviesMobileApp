package com.example.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo("id")
    val id : Int,
    @ColumnInfo("movie")
    val movie : String,
    @ColumnInfo("rating")
    val rating : Double,
    @ColumnInfo("image")
    val image : String,
    @ColumnInfo("imdb_url")
    val imdb_url : String
)