package com.project.smartnews.common

import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.network.ArticleResponse

fun ArticleResponse.asDatabaseModel(category: String): ArticleEntity {
    val sourceName = this.source.name
    val articleAuthor = this.author ?: ""
    val articleContent = this.content ?: ""
    val articleDescription = this.description ?: ""
    val articlePublishedAt = this.publishedAt ?: ""
    val articleTitle = splitTitle(this.title) ?: ""
    val articleUrl = this.url ?: ""
    val articleUrlToImage = this.urlToImage ?: ""

    return ArticleEntity(
            category = category,
            author = articleAuthor,
            content = articleContent,
            description = articleDescription,
            publishedAt = articlePublishedAt,
            sourceName = sourceName,
            title = articleTitle,
            url = articleUrl,
            urlToImage = articleUrlToImage
        )
}

fun splitTitle(title : String): String {
    return title.split(" - ").get(0)
}