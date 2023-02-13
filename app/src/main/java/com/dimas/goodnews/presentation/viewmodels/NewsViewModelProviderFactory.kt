package com.dimas.goodnews.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.goodnews.data.network.NewsRepositoryImpl


class NewsViewModelProviderFactory(
    val app: Application,
    val newsRepository: NewsRepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StartViewModel(app, newsRepository) as T
    }



}