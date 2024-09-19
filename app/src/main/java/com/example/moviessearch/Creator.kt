package com.example.moviessearch

import com.example.moviessearch.data.MoviesRepositoryImpl
import com.example.moviessearch.data.network.RetrofitNetworkClient
import com.example.moviessearch.domain.api.MoviesInteractor
import com.example.moviessearch.domain.api.MoviesRepository
import com.example.moviessearch.domain.implement.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}