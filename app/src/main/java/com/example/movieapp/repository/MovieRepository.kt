package com.example.movieapp.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.local.Movie
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.remote.MovieResponse
import com.example.movieapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

 class MovieRepository(private val movieDao: MovieDao) {
     fun getAllFavorites(): Flow<List<Movie>> = movieDao.getAllFavorites()

     suspend fun searchMovies(query: String): Response<MovieResponse> {
        return RetrofitInstance.api.searchMovies(BuildConfig.MOVIE_API_KEY, query)
    }

     suspend fun addFavorite(movie: Movie) = movieDao.addFavorite(movie)

     suspend fun removeFavorite(movie: Movie) = movieDao.removeFavorite(movie)
}
