package com.project.smartnews.data.repo

import androidx.lifecycle.LiveData
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.model.NewsCategoryEntity
import kotlinx.coroutines.*
import javax.inject.Inject


class UserCategoryRepository @Inject constructor(
    private val db: NewsDatabase
) {

    fun updateUserCategorySelection(updateSelectedCategory: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val getSelected = db.NewsCategoryDao().checkCategorySelected(updateSelectedCategory)
            db.NewsCategoryDao().updateSelectedCategory(updateSelectedCategory, !getSelected)
        }
    }

    fun checkIfCategorySelected(category: String): Boolean = runBlocking {
        var selected: Boolean = true
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            selected = db.NewsCategoryDao().checkCategorySelected(category)
            return@async selected
        }
        waitFor.await()
        return@runBlocking selected
    }

    fun getAll(): LiveData<List<NewsCategoryEntity>> {
        return db.NewsCategoryDao().getAllNewsCategoryAsLiveData()
    }

    val getAllNewsCategorySelection: List<NewsCategoryEntity> = runBlocking{
        lateinit var categories: List<NewsCategoryEntity>

        val waitFor = CoroutineScope(Dispatchers.IO).async {
            categories = db.NewsCategoryDao().getAllNewsCategory()
            return@async categories
        }
        waitFor.await()

        println("heh category has probably being updated! ${categories.toString()}")

        return@runBlocking categories
    }
    
    val getUserNewsCategorySelection : List<NewsCategoryEntity> = runBlocking {
        lateinit var categories: List<NewsCategoryEntity>

        val waitFor = CoroutineScope(Dispatchers.IO).async {
            categories = db.NewsCategoryDao().getUserSelectedCategory()
            return@async categories
        }
        waitFor.await()

        println("from UserCategoryRepo, there are ${categories.size} USER categories")

        return@runBlocking categories
    }

}