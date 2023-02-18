package com.dimas.goodnews.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.goodnews.data.network.NewsRepositoryImpl
import com.dimas.goodnews.data.network.models.Article
import kotlinx.coroutines.launch

open class SaveViewModel(
    val newsRepository: NewsRepositoryImpl
) : ViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertNewsToDb(article)
    }

    fun getSavedArticles() = newsRepository.getSavedNews()


    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }


}

