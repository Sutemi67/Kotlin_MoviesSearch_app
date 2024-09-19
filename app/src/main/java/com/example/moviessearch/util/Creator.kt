package com.example.moviessearch.util

import android.content.Context
import com.example.moviessearch.data.MoviesRepositoryImpl
import com.example.moviessearch.data.network.RetrofitNetworkClient
import com.example.moviessearch.domain.api.MoviesInteractor
import com.example.moviessearch.domain.api.MoviesRepository
import com.example.moviessearch.domain.implement.MoviesInteractorImpl
import com.example.moviessearch.presentation.movies.MoviesSearchPresenter
import com.example.moviessearch.presentation.movies.MoviesView

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        moviesView: MoviesView,
        context: Context
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(
            view = moviesView,
            context = context
        )
    }
}