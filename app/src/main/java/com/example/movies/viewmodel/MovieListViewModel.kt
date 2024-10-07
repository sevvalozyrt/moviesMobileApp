package com.example.movies.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Movie
import com.example.movies.roomdb.MovieDatabase
import com.example.movies.service.MovieAPIService
import com.example.movies.util.SpecialSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    val movies = MutableLiveData<List<Movie>>()
    val movieErrorMessage = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    private val movieAPIService = MovieAPIService()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())

    private val guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val kaydedilmeZamani = specialSharedPreferences.zamaniAl()

        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani) {
            verileriRoomdanAl()
        } else {
            verileriInternettenAl()
        }
    }

    fun refreshDataFromInternet() {
        verileriInternettenAl()
    }

    private fun verileriRoomdanAl() {
        movieLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val movieList = MovieDatabase(getApplication()).movieDao().getAllMovies()
            withContext(Dispatchers.Main) {
                filmleriGoster(movieList)
                Toast.makeText(getApplication(), "Verileri Roomdan Aldık", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verileriInternettenAl() {

        movieLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovies = movieAPIService.getData()
            withContext(Dispatchers.Main) {
                movieLoading.value = false
                roomaKaydet(fetchedMovies)
                filmleriGoster(fetchedMovies)
                Toast.makeText(getApplication(), "Verileri Internetten Aldık", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun filmleriGoster(movieList : List<Movie>) {
        movies.value = movieList
        movieErrorMessage.value = false
        movieLoading.value = false
    }

    private fun roomaKaydet(movieList : List<Movie>) {

        viewModelScope.launch {

            val dao = MovieDatabase(getApplication()).movieDao()
            dao.deleteAllMovies()
            dao.insertAll(*movieList.toTypedArray())

            filmleriGoster(movieList)
        }
        specialSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}