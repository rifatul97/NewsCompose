package com.project.smartnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity (
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    @PrimaryKey
    val url: String,
    val category: String,
    val urlToImage: String,
    val sourceName: String
)