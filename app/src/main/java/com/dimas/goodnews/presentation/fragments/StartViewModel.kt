package com.dimas.goodnews.presentation.fragments

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.goodnews.data.network.NewsRepositoryImpl
import com.dimas.goodnews.data.network.models.NewsResponse
import com.dimas.goodnews.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class StartViewModel(app: Application, val newsRepository: NewsRepositoryImpl) : ViewModel() {

    val startNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getStartNews("ua")
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


}