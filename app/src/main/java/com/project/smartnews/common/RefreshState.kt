package com.project.smartnews.common

import androidx.annotation.StringRes
import com.project.smartnews.model.ArticleEntity

/* A generic class that contains data and status about loading this data.
sealed class RefreshState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RefreshState<T>(data)
    class Loading<T>(data: T? = null) : RefreshState<T>(data)
    class Error<T>(message: String, data: T? = null) : RefreshState<T>(data, message)
} */

sealed class RefreshState {
    object Loading : RefreshState()
    object Loaded : RefreshState()
    object PermissionError : RefreshState()
    data class Error(@StringRes val message: Int) : RefreshState()
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

data class ArticleListState(
    val isLoading: Boolean = false,
    val articles: HashMap<String, List<ArticleEntity>> = HashMap(),
    val error: String = ""
)

data class NewsCategoryListState(
    val categories: List<String> = emptyList(),
    val error: String = ""
)