package com.project.smartnews.ui.user

import androidx.lifecycle.ViewModel
import com.project.smartnews.data.repo.UserCategoryRepository
import com.project.smartnews.model.NewsCategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository
): ViewModel() {

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
