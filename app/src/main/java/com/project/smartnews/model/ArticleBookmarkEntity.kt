package com.project.smartnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_bookmarks")
data class ArticleBookmarkEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bookmark_id")
    val id: Int?,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val category: String,
    val urlToImage: String,
    val sourceName: String,

    val timeAdded: Long
)

