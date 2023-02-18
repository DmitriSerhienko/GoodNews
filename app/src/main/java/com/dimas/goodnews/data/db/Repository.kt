package com.dimas.goodnews.data.db

import android.content.Context
import androidx.room.Room

object Repository {

    private lateinit var appContext: Context

    val database: ArticleDatabase by lazy<ArticleDatabase> {
        Room.databaseBuilder(
            appContext,
            ArticleDatabase::class.java,
            "article_database"
        ).build()
    }

    fun init(context: Context) {
        appContext = context
    }

}