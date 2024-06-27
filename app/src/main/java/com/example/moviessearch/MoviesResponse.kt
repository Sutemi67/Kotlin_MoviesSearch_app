package com.example.moviessearch

class MoviesResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
)