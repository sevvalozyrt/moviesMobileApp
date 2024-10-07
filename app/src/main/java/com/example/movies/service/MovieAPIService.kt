package com.example.movies.service

import com.example.movies.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {
    private val BASE_URL = "https://dummyapi.online/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    suspend fun getData() : List<Movie> {
        return retrofit.getMovie()
    }

}