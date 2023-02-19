package com.dimas.goodnews.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.goodnews.data.network.NewsRepositoryImpl
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.data.network.models.NewsResponse
import com.dimas.goodnews.utils.Resource
import kotlinx.coroutines.launch

class NewsViewModel(val app: Application, val newsRepository: NewsRepositoryImpl) : ViewModel() {

    val startNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getStartNews("ua")
        getSearchNews("")
    }

    private fun getStartNews(countryCode: String) = viewModelScope.launch {
        startNews.postValue(Resource.Loading())
        val response = newsRepository.getStartNews(countryCode = countryCode, page = newsPage)
        if (response.isSuccessful) {
            response.body().let { res ->
                startNews.postValue(Resource.Success(res))
            }
        } else {
            startNews.postValue(Resource.Error(message = response.message()))
        }

    }
    fun getSearchNews(query: String) =
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response = newsRepository.getSearchNews(query, searchNewsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchNewsLiveData.postValue(Resource.Success(res))
                }
            } else {
                searchNewsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertNewsToDb(article)
    }

    fun getSavedArticles() = newsRepository.getSavedNews()


    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }


}