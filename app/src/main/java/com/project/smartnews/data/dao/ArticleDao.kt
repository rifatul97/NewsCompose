package com.project.smartnews.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.smartnews.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("Select * from article")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("Select * from article where url = :url")
    fun getArticleById(url: String): Flow<ArticleEntity>

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM article WHERE category = :categoryName")
    suspend fun deleteArticlesByCategory(categoryName: String)

    @Query("Select * from article where category = :categoryName")
    fun getAllArticlesByCategory(categoryName: String): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticleEntity>)

    @Transaction
    suspend fun saveArticles(categoryName: String, articles: List<ArticleEntity>) {
        deleteArticlesByCategory(categoryName)
        insertAll(articles)
    }
}