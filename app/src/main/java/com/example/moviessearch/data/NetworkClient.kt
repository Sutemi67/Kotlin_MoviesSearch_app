package com.example.moviessearch.data

import com.example.moviessearch.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}