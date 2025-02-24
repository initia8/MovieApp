package com.example.movieapp.ui.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.local.Movie
import com.google.gson.Gson

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieTitle = findViewById<TextView>(R.id.movie_detail_title)
        val moviePoster = findViewById<ImageView>(R.id.movie_detail_poster)
        val movieOverview = findViewById<TextView>(R.id.movie_detail_overview)

        val movieJson = intent.getStringExtra("movie")
        val movie = Gson().fromJson(movieJson, Movie::class.java)
        movie?.let {
            movieTitle.text = it.title
            movieOverview.text = it.overview
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + it.poster_path).into(moviePoster)
        }
    }
}