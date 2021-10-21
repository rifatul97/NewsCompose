package com.project.smartnews.ui.bookmarks

import androidx.lifecycle.ViewModel
import com.project.smartnews.data.repo.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    val bookmarks = bookmarkRepository.getAll()

    /*fun refreshBookmarksFromDB() = runBlocking {
        lateinit var articleBookmarks: List<ArticleBookmarkEntity>
        val waitFor = async {
            articleBookmarks = bookmarkRepository.getAllBookmarks()
        }
        waitFor.await()

        _bookmarks.value = articleBookmarks

        return@runBlocking _bookmarks
    }*/

    fun checkIfArticleBookmarked(articleId: Long): Boolean {
        return bookmarkRepository.checkIfArticleBookmarked(articleId)
    }

    fun insertBookmark(articleId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val check = bookmarkRepository.checkIfArticleBookmarked(articleId)
            if (check) {
                println("article $articleId is bookmarked so removing it from bookmark db")
                bookmarkRepository.removeBookmark(articleId)
            } else {
                println("article $articleId is not bookmarked so adding it to the db")
                bookmarkRepository.insertBookmark(articleId)
            }
        }

    }

}