package com.example.moviessearch.data

import com.example.moviessearch.data.dto.MoviesSearchRequest
import com.example.moviessearch.data.dto.MoviesSearchResponse
import com.example.moviessearch.domain.api.MoviesRepository
import com.example.moviessearch.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description)
            }
        } else {
            emptyList()
        }
    }
}