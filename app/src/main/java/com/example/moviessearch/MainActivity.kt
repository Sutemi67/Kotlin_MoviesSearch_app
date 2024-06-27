package com.example.moviessearch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val imdbBaseUrl = "https://tv-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApi::class.java)

    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView

    private val movies = ArrayList<Movie>()

    private val adapter = MoviesAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        placeholderMessage = findViewById(R.id.placeholderMessage)
        searchButton = findViewById(R.id.searchButton)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.locations)

        adapter.movies = movies

        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        searchButton.setOnClickListener {
            if (queryInput.text.isNotEmpty()) {
                imdbService.findMovie(queryInput.text.toString()).enqueue(object :
                    Callback<MoviesResponse> {
                    override fun onResponse(
                        call: Call<MoviesResponse>,
                        response: Response<MoviesResponse>
                    ) {
                        if (response.code() == 200) {
                            movies.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if (movies.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                showMessage("", "")
                            }
                        } else {
                            showMessage(
                                getString(R.string.something_went_wrong),
                                response.code().toString()
                            )
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        showMessage(getString(R.string.something_went_wrong), t.message.toString())
                    }
                })
            }
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
}