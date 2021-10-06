package com.project.smartnews.common

import androidx.annotation.StringRes

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