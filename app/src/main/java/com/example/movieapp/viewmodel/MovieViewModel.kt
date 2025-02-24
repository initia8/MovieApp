package com.example.movieapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.movieapp.data.local.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val favoriteMovies : LiveData<List<Movie>> = repository.getAllFavorites().asLiveData()

    fun getMovies(query: String) = liveData(Dispatchers.IO) {
        val response = repository.searchMovies(query)
        emit(response.body()?.results ?: emptyList())
    }

    fun addFavorite(movie: Movie) = liveData(Dispatchers.IO) {
        repository.addFavorite(movie)
        emit(Unit)
    }

    fun removeFavorite(movie: Movie) = liveData(Dispatchers.IO) {
        repository.removeFavorite(movie)
        emit(Unit)
    }
}


