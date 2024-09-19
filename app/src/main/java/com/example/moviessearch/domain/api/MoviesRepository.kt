package com.example.moviessearch.domain.api

import com.example.moviessearch.domain.models.Movie
import com.example.moviessearch.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}