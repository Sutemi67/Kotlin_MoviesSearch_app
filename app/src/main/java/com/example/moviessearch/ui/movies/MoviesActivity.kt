package com.example.moviessearch.ui.movies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearch.Creator
import com.example.moviessearch.R
import com.example.moviessearch.domain.api.MoviesInteractor
import com.example.moviessearch.domain.models.Movie
import com.example.moviessearch.ui.posters.PosterActivity

class MoviesActivity : Activity() {

    private val moviesInteractor = Creator.provideMoviesInteractor()

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val movies = ArrayList<Movie>()

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { searchRequest() }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Этот метод остался без изменений
    }

    override fun onDestroy() {
        // Этот метод остался без изменений
    }

    private fun searchRequest() {
        if (queryInput.text.isNotEmpty()) {

            placeholderMessage.visibility = View.GONE
            moviesList.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            moviesInteractor.searchMovies(
                queryInput.text.toString(),
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>) {
                        handler.post {
                            progressBar.visibility = View.GONE
                            movies.clear()
                            movies.addAll(foundMovies)
                            moviesList.visibility = View.VISIBLE
                            adapter.notifyDataSetChanged()
                            if (movies.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                hideMessage()
                            }
                        }
                    }
                })
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        // Этот метод остался без изменений
    }

    private fun hideMessage() {
        // Этот метод остался без изменений
    }

    private fun clickDebounce(): Boolean {
        // Этот метод остался без изменений
    }

    private fun searchDebounce() {
        // Этот метод остался без изменений
    }
}