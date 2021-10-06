package com.project.smartnews.data.repo

import com.project.smartnews.common.RefreshState
import com.project.smartnews.common.asDatabaseModel
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.model.UserEntity
import com.project.smartnews.network.ArticleResponse
import com.project.smartnews.network.WebService
import com.project.smartnews.network.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsService: WebService,
    private val db: NewsDatabase
) {

    val currentArticles = db.ArticleDao().getAllArticles()
        .stateIn(CoroutineScope(Dispatchers.IO), Eagerly, emptyList())

    private val _refreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)
    val refreshState: StateFlow<RefreshState> get() = _refreshState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (db.UserDao().selectAll().isEmpty()) {
                println("inserting userentity..")
                db.UserDao().insert(UserEntity(1, 0))
            }
        }
    }
    private suspend fun deleteAndInsertNewArticles(
        articleResponses: List<ArticleResponse>,
        category: String
    ) {
        val articles = articleResponses.map {
            it.asDatabaseModel(category)
        }
        db.ArticleDao().saveArticles(category, articles)
    }

    fun refreshSelectedCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshArticles()
        }
    }

    fun refreshArticles() {
        CoroutineScope(Dispatchers.IO).launch {
            val start = System.nanoTime()
            val userLastRefreshed = db.UserDao().getLastRefreshedTime()
            println("last refresh was ${(start - userLastRefreshed) / (1000000000)}")

            if ((start - userLastRefreshed) / (1000000000) > 60) {
                println("so borrowing articles from network!")
                //_refreshState.emit(RefreshState.Loading)

                newsService.getTopHeadings().run {
                    when (this) {
                        is Result.Success -> {
                            this.run {
                                deleteAndInsertNewArticles(this.articles, "General")
                                db.UserDao().insertRefreshTime(System.nanoTime())
                            }
                        }
                        else -> {
                            println(this.toString())
                        }
                    }
                }
            }
        }
    }

}
