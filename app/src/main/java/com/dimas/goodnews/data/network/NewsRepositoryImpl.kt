package com.dimas.goodnews.data.network

import androidx.lifecycle.LiveData
import com.dimas.goodnews.data.db.Repository
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.data.network.models.NewsResponse
import com.dimas.goodnews.domain.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepositoryImpl : NewsRepository {

    private var db = Repository.database

    override suspend fun getStartNews(countryCode: String, page: Int): Response<NewsResponse> {
        return RetrofitInstance.apiService.getStartNews(countryCode, page)
    }

    override suspend fun getSearchNews(searchQuery: String, page: Int): Response<NewsResponse> {
        return RetrofitInstance.apiService.getSearchNews(searchQuery, page)
    }

    override suspend fun insertNewsToDb(article: Article) = withContext(Dispatchers.IO) {
        db.getArticleDao().insert(article)
    }

    override fun getSavedNews(): LiveData<List<Article>> =
        db.getArticleDao().getAllArticles()

    override suspend fun deleteArticle(article: Article) = withContext(Dispatchers.IO) {
        db.getArticleDao().delete(article)
    }


}