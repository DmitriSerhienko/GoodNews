package com.dimas.goodnews.data.network.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)