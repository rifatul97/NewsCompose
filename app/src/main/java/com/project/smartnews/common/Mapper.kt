package com.project.smartnews.common

import com.project.smartnews.model.ArticleBookmarkEntity
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
            id = null,
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

fun ArticleEntity.toBookmarkModel(): ArticleBookmarkEntity {
    return ArticleBookmarkEntity(
        id = this.id,
        category = category,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceName = sourceName,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
        timeAdded = System.nanoTime()
    )
}

fun ArticleBookmarkEntity.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        category = category,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceName = sourceName,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun splitTitle(title : String): String {
    return title.split(" - ").get(0)
}