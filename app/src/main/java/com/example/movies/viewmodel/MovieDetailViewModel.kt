package com.example.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Movie
import com.example.movies.roomdb.MovieDatabase
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    val movieLiveData = MutableLiveData<Movie>()
    fun roomVerisiniAl(id : Int) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            val movie = dao.getMovie(id)
            movieLiveData.value = movie
        }
    }
}