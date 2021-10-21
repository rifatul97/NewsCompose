package com.project.smartnews.data.dao

import androidx.room.*
import com.project.smartnews.common.Constants
import com.project.smartnews.common.asDatabaseModel
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.network.ArticleResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("Select * from article")
    fun getAllArticles(): List<ArticleEntity>

    @Query("Select * from article where id = :articleId")
    fun getArticleById(articleId: Int): Flow<ArticleEntity>

    @Query("Select * from article where id = :articleId")
    fun getArticleByIdAsFlow(articleId: Int): ArticleEntity

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM article WHERE category = :categoryName")
    suspend fun deleteArticlesByCategory(categoryName: String)

    @Query("Select * from article where category = :categoryName")
    fun getAllArticlesByCategory(categoryName: String): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticleEntity>)

    @Transaction
    suspend fun saveArticles(categoryName: String, articleResponses: List<ArticleResponse>) {
        println("${articleResponses.size} $categoryName articles has been fetched~")

        val articles = articleResponses
            .map { it.asDatabaseModel(categoryName) }
            .filter { article ->
                Constants.dataProvider.any {
                    it  == article.sourceName
                }
            }

        println(articles.toString())

        deleteArticlesByCategory(categoryName)
        insertAll(articles)
    }
}