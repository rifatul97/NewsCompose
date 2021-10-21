package com.project.smartnews.ui.articles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartnews.common.ArticleListState
import com.project.smartnews.data.repo.ArticleRepository
import com.project.smartnews.data.repo.UserCategoryRepository
import com.project.smartnews.model.NewsCategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val userCategoryRepository: UserCategoryRepository,
    //private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private var _isLoading = MutableStateFlow(true)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _articleListState = MutableLiveData(ArticleListState())
    val articleListState: MutableLiveData<ArticleListState> get() = _articleListState

    private val _categories = MutableStateFlow(emptyList<NewsCategoryEntity>()) // 1
    val categories: StateFlow<List<NewsCategoryEntity>> get() = _categories

    val category = userCategoryRepository.getAll()

    fun loadCategory() = runBlocking {
        lateinit var cat: List<NewsCategoryEntity>
        val waitFor = async {
            cat = userCategoryRepository.getAllNewsCategorySelection
        }
        waitFor.await()

        _categories.value = cat

        return@runBlocking _categories
    }

    private fun effect(block: suspend () -> Unit) {
        //_isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) { block() }    // 4
        //_isLoading.value = false
    }

    private suspend fun loadArticles(forceRefresh: Boolean = false) {
        _articleListState.value?.let {
            articleRepository.refreshArticles(
                categories.value,
                it.articles,
                forceRefresh,
                _isLoading
            )
        }
    }

    suspend fun forceRefreshArticles() {
        _isLoading.value = false
        loadArticles(true)
    }

    init {
        viewModelScope.launch {
            loadCategory()
            loadArticles()
        }
    }

    fun getUserNewsCategorySelected(category: String): Boolean {
        return userCategoryRepository.checkIfCategorySelected(category)
    }

    fun updateSelection(categoryName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userCategoryRepository.updateUserCategorySelection(categoryName)
        }
    }

    fun getAllNewsCategory(): List<NewsCategoryEntity> {
        return userCategoryRepository.getAllNewsCategorySelection
    }

}

