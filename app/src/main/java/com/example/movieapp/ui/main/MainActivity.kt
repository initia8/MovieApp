package com.example.movieapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.ui.favourite.FavouriteActivity
import com.example.movieapp.ui.details.MovieDetailActivity
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory
import com.example.movieapp.R
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.repository.MovieRepository
import com.google.gson.Gson

import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button
    private lateinit var favButton: Button
    private lateinit var recyclerView: RecyclerView

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(MovieDatabase.getDatabase(applicationContext).movieDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchInput = findViewById(R.id.search_input)
        searchButton = findViewById(R.id.search_button)
        favButton = findViewById(R.id.fav_button)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener {
            val query = searchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Toast.makeText(this, "Enter a movie title", Toast.LENGTH_SHORT).show()
            }
        }

        favButton.setOnClickListener {
            startActivity(Intent(this, FavouriteActivity::class.java))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun searchMovies(query: String) {
        lifecycleScope.launch {
            val movies = viewModel.getMovies(query)
            if (movies != null) {
                recyclerView.adapter = MovieAdapter( viewModel) { movie ->
                    val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                           intent.putExtra("movie", Gson().toJson(movie))
                           startActivity(intent)
                }

            } else {
                Toast.makeText(this@MainActivity, "No results found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

