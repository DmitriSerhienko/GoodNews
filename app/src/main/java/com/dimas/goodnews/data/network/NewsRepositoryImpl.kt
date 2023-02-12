package com.dimas.goodnews.data.network

import com.dimas.goodnews.data.network.models.NewsResponse
import com.dimas.goodnews.domain.NewsRepository
import retrofit2.Response

class NewsRepositoryImpl: NewsRepository {

    override suspend fun getStartNews(countryCode: String, page: Int) : Response<NewsResponse> {
        return RetrofitInstance.apiService.getStartNews(countryCode, page)
    }

    override suspend fun getSearchNews(searchQuery: String, page: Int): Response<NewsResponse> {
        return RetrofitInstance.apiService.getSearchNews(searchQuery, page)
    }


}