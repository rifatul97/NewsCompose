package com.project.smartnews.ui.articles

import androidx.compose.runtime.MutableState
import com.project.smartnews.model.ArticleEntity

data class ArticleListState(
    val isLoading: Boolean = false,
    //val articles: HashMap<String, List<ArticleEntity>> = HashMap(),
    val articles: List<ArticleEntity> = emptyList(),
    val error: String = ""
)