package com.project.smartnews.data.repo

import androidx.lifecycle.LiveData
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.model.ArticleBookmarkEntity
import kotlinx.coroutines.*
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val db: NewsDatabase
) {

    fun checkIfArticleBookmarked(articleId: Long): Boolean = runBlocking {
        var articleExist: Boolean = true
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            val article = db.BookmarkDao().checkIfFavorite(articleId)
            if (article == null) {
                println("nothing in the list")
                articleExist = false
            } else {
                println("there is - ${article.toString()}")
            }
            return@async articleExist
        }
        waitFor.await()
        println("articleExist is $articleExist")

        return@runBlocking articleExist
    }

    fun insertBookmark(articleId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val article = db.ArticleDao().getArticleByIdAsFlow(articleId.toInt())
            db.BookmarkDao().insertArticleBookmark(article)
        }
    }

    fun removeBookmark(articleId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            db.BookmarkDao().removeBookmark(articleId)
        }
    }

    /*fun getAllBookmarks(): List<ArticleBookmarkEntity> {
        return db.BookmarkDao().getArticlesByLastRead()
    }*/

    fun getAll(): LiveData<List<ArticleBookmarkEntity>> {
        return db.BookmarkDao().getArticlesByLastRead()
    }

}