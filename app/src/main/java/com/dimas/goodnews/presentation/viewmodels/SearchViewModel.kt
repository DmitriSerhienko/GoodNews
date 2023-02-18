package com.dimas.goodnews.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.goodnews.data.network.NewsRepositoryImpl
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.data.network.models.NewsResponse
import com.dimas.goodnews.domain.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    val newsRepository = NewsRepositoryImpl()


    init {
        getSearchNews("")
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

}