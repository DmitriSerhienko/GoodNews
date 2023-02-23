package com.dimas.goodnews.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.dimas.goodnews.data.network.models.Article

object NewsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}