package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String
)
