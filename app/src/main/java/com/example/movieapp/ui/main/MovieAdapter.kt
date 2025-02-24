package com.example.movieapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.data.local.Movie


class MovieAdapter(
    private val viewModel: MovieViewModel,
    private val onMovieClick: (Movie) -> Unit  // Click listener for movie details
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()  // Store movie data separately

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, viewModel, onMovieClick)
    }

    override fun getItemCount() = movieList.size


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, viewModel: MovieViewModel, onMovieClick: (Movie) -> Unit) {
            val title: TextView = itemView.findViewById(R.id.movie_title)
            val poster: ImageView = itemView.findViewById(R.id.movie_poster)
            val favoriteButton: Button = itemView.findViewById(R.id.favorite_button)

            title.text = movie.title
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(poster)

            favoriteButton.setOnClickListener { viewModel.addFavorite(movie) }

            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }
}

