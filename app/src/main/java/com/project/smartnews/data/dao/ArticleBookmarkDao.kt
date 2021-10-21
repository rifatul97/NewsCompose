package com.project.smartnews.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.smartnews.common.toBookmarkModel
import com.project.smartnews.model.ArticleBookmarkEntity
import com.project.smartnews.model.ArticleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Dao
interface ArticleBookmarkDao {

    @Query("Select * from user_bookmarks WHERE bookmark_id = :articleId")
    fun checkIfFavorite(articleId: Long): ArticleBookmarkEntity

    @Query("Select * from user_bookmarks ORDER BY timeAdded")
    fun getArticlesByLastRead(): LiveData<List<ArticleBookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(article: ArticleBookmarkEntity)

    @Query("Delete FROM user_bookmarks WHERE bookmark_id = :articleId")
    fun removeBookmark(articleId: Long)

    @Transaction
    fun insertArticleBookmark(article: ArticleEntity) {
        val article = article.toBookmarkModel()
        CoroutineScope(Dispatchers.IO).launch {
            insertBookmark(article)
        }
    }

}