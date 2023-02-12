package com.dimas.goodnews.domain

import com.dimas.goodnews.data.network.models.NewsResponse
import retrofit2.Response

interface NewsRepository {

    suspend fun getStartNews(countryCode: String, page: Int): Response<NewsResponse>

    suspend fun getSearchNews(searchQuery: String, page: Int): Response<NewsResponse>

}