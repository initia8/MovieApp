package com.example.movieapp.ui.favourite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.ui.details.MovieDetailActivity
import com.example.movieapp.ui.main.MovieAdapter
import com.google.gson.Gson

class FavouriteActivity : AppCompatActivity() {
    private val viewModel: MovieViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MovieViewModel(MovieRepository(MovieDatabase.getDatabase(this@FavouriteActivity).movieDao())) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        val recyclerView: RecyclerView = findViewById(R.id.fav_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.favoriteMovies.observe(this) { movie ->
            recyclerView.adapter = MovieAdapter(viewModel) { movie ->
                val intent = Intent(this, MovieDetailActivity::class.java)
                intent.putExtra("movie", Gson().toJson(movie))
                startActivity(intent)
            }
        }
    }
}