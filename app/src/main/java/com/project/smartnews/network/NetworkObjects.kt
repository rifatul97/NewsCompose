package com.project.smartnews.network

sealed class Result {
    data class Success(
        val status: String,
        val totalResults: Int,
        val articles: List<ArticleResponse>,
    ) : Result()

    data class Failure(
        val status: String,
    ) : Result()
}

data class ApiResponse(
    val articles: List<ArticleResponse>,
    val status: String,
    val totalResults: Int
)

data class ArticleResponse(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceResponse,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class SourceResponse(
    val id: Any,
    val name: String
)