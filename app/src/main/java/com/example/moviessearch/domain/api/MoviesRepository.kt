package com.example.moviessearch.domain.api

import com.example.moviessearch.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}