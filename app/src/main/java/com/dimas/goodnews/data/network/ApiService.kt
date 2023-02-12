package com.dimas.goodnews.data.network

import com.dimas.goodnews.data.network.models.NewsResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/top-headlines")
    suspend fun getStartNews(
        @Query("country") countryCode: String = "ua",
        @Query("page") page: Int = 2,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun getSearchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 2,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<NewsResponse>

    companion object{
        private const val API_KEY = "a88ce70ab95f4f0c8732b219e419b192"
    }
}