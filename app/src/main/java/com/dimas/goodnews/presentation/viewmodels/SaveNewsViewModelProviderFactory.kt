package com.dimas.goodnews.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.goodnews.data.network.NewsRepositoryImpl


class SaveNewsViewModelProviderFactory(
    val newsRepository: NewsRepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SaveViewModel(newsRepository) as T
    }


}