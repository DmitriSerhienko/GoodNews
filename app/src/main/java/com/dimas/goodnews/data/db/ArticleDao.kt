package com.dimas.goodnews.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dimas.goodnews.data.network.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}

