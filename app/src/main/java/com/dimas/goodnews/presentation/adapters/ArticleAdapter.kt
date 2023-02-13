package com.dimas.goodnews.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.databinding.ItemBinding

class ArticleAdapter(private val context: Context) :
    ListAdapter<Article, ArticleViewHolder>(NewsDiffCallback) {

    var onArticleClickListener: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        with(holder.binding) {
            Glide.with(context).load(article.urlToImage).into(articleImage)
            articleImage.clipToOutline = true
            articleTitle.text = article.title
            articleData.text = article.publishedAt
            root.setOnClickListener {
                onArticleClickListener?.invoke(article)
            }
        }
    }


//    interface OnNewsClickListener {
//        fun onNewsClick(article: Article)
//    }

}