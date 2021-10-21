package com.project.smartnews.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.smartnews.data.repo.ArticleRepository
import com.project.smartnews.network.ArticleResponse
import com.project.smartnews.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
): ViewModel() {
    val showDialog = mutableStateOf(false)

    //check internet connection
    fun getArticlesByText(query: String): List<ArticleResponse> {
        try {
            var articles = articleRepository.getArticlesByText(query).run {
                when (this) {
                    is Result.Success -> {
                        return this.articles
                    }
                    else -> {
                        return emptyList()
                    }
                }
            }
        } catch (e : HttpException) {
            println("article not found..")
        }

        return emptyList()
    }

}