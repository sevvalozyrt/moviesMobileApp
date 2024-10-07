package com.example.movies.service

import com.example.movies.model.Movie
import retrofit2.http.GET

interface MovieAPI {

    //BASE URL -> https://dummyapi.online/
    //ENDPOINT -> api/movies

    @GET("api/movies")
    suspend fun getMovie() : List<Movie>

}