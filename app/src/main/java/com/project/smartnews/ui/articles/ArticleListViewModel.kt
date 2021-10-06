package com.project.smartnews.ui.articles

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartnews.common.RefreshState
import com.project.smartnews.data.repo.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._androidx_hilt_lifecycle_ViewModelFactoryModules_ActivityModuleModuleDeps
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    repo: ArticleRepository
) : ViewModel() {

    private val refreshArticles = repo.refreshArticles()

    private fun refresh() {
        println("refresh articles from articleViewModel")
        viewModelScope.launch {
            refreshArticles
        }
    }

    init {
        refresh()
    }

    val getArticles = repo.currentArticles
}



