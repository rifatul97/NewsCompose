package com.project.smartnews.data.repo

import androidx.lifecycle.LiveData
import com.project.smartnews.common.Constants
import com.project.smartnews.common.toBookmarkModel
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.model.ArticleBookmarkEntity
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.model.NewsCategoryEntity
import com.project.smartnews.network.Result
import com.project.smartnews.network.WebService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsService: WebService,
    private val db: NewsDatabase
) {

    suspend fun refreshArticles(
        categories: List<NewsCategoryEntity>,
        articles: HashMap<String, List<ArticleEntity>>,
        forceRefresh: Boolean,
        _isLoading: MutableStateFlow<Boolean>
    ) = runBlocking {
        val waitForActivity = async {
            CoroutineScope(Dispatchers.IO).launch {
                val userLastRefreshedAt = db.UserDao().getLastRefreshedTime()
                println("user last refreshed at second = $userLastRefreshedAt")
                val userLastRefreshed = ((System.nanoTime() - userLastRefreshedAt) / (1000000000))
                if ((userLastRefreshed > 64000) || forceRefresh) {
                    println("Last Refreshed was $userLastRefreshed and that means articles must be fetched!")
                    //categories.fetchByNetwork()
                    fetchByNetwork(categories)
                }
            }
        }

        waitForActivity.await().invokeOnCompletion {
            println("articles loading successful")
            CoroutineScope(Dispatchers.IO).launch {
                loadArticles(categories, articles, _isLoading)
            }
        }

    }


    private fun loadArticles(
        categories: List<NewsCategoryEntity>,
        articles: HashMap<String, List<ArticleEntity>>,
        isLoading: MutableStateFlow<Boolean>
    ) = runBlocking {
        var i = 0
        println("loading articles from LOADARTICLES")

        val jobs: List<Job> = (categories).map { category ->
            launch(context = Dispatchers.Default) {
                coroutineScope {
                    launch {
                        articles[category.categoryName] =
                            db.ArticleDao().getAllArticlesByCategory(category.categoryName)
                        i++
                        delay(100)
                    }
                }
            }
        }
        jobs.joinAll()

        if (i == categories.size) {
            isLoading.value = false
        }
    }

    private fun fetchByNetwork(categories: List<NewsCategoryEntity>) = runBlocking {
        val jobs: List<Job> = (categories).map { newsCategoryEntity ->
            launch(context = Dispatchers.Default) {
                coroutineScope {
                    launch {
                        try {
                            val category = newsCategoryEntity.categoryName
                            if (category != "Top Heading") {
                                newsService.getArticlesByCategory(
                                    Constants.DEFAULT_COUNTRY,
                                    category,
                                    Constants.API_KEY
                                )
                                    .run {
                                        when (this) {
                                            is Result.Success -> {
                                                this.run {
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        db.ArticleDao().saveArticles(
                                                            category,
                                                            articles
                                                        )
                                                    }
                                                }
                                            }
                                            else -> {

                                            }
                                        }
                                    }
                            } else {
                                newsService.getTopHeadings(
                                    Constants.DEFAULT_COUNTRY,
                                    Constants.API_KEY
                                )
                                    .run {
                                        when (this) {
                                            is Result.Success -> {
                                                this.run {
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        db.ArticleDao().saveArticles(
                                                            "Top Heading",
                                                            articles
                                                        )
                                                    }
                                                }
                                            }
                                            else -> {

                                            }
                                        }
                                    }
                            }

                        } catch (e: HttpException) {
                            println(e.localizedMessage)
                        } catch (e: IOException) {

                        }
                        delay(100)
                    }
                }
            }
        }

        jobs.joinAll()
        CoroutineScope(Dispatchers.IO).launch {
            db.UserDao().insertRefreshTime(System.nanoTime())
        }
    }

    fun getArticlesByText(text: String): Result = runBlocking {
        lateinit var articles: Result

        val waitFor = CoroutineScope(Dispatchers.IO).async {
            articles = newsService.fetchArticlesByText(text, Constants.API_KEY)
            return@async articles
        }
        waitFor.await()

        return@runBlocking articles
    }

    fun getArticleById(articleId: Int): Flow<ArticleEntity> {
        return db.ArticleDao().getArticleById(articleId)
    }

    fun getBookmarks(): LiveData<List<ArticleBookmarkEntity>> {
        return db.BookmarkDao().getArticlesByLastRead()
    }

    fun updateBookmark(article: ArticleEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            db.BookmarkDao().insertBookmark(article.toBookmarkModel())
        }
    }

    suspend fun loadBookmarkById(articleId: Long): ArticleBookmarkEntity {
        return db.BookmarkDao().checkIfFavorite(articleId)
    }

    fun checkIfArticleBookmarked(articleId: Long) = runBlocking {
        val deferred: Deferred<ArticleBookmarkEntity> = async {
            loadBookmarkById(articleId)
        }
        deferred.await()
    }

    fun removeBookmark(articleId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            db.BookmarkDao().removeBookmark(articleId)
        }
    }
}
