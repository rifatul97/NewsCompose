package com.project.smartnews.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.smartnews.model.NewsCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCategory(newsCategory: NewsCategoryEntity)

    @Query("Select * from userCategorySelected")
    fun getAllNewsCategory() : List<NewsCategoryEntity>

    @Query("UPDATE userCategorySelected SET categorySelected = :selected WHERE categoryName = :categoryName")
    fun updateSelectedCategory(categoryName : String, selected: Boolean)

    @Query("Select categorySelected from userCategorySelected WHERE categoryName = :categoryName")
    fun checkCategorySelected(categoryName: String): Boolean

    @Query("Select * from userCategorySelected WHERE categorySelected = 1")
    fun getUserSelectedCategory() : List<NewsCategoryEntity>

    @Query("Select * from userCategorySelected")
    fun getCategoriesFlow(): Flow<List<NewsCategoryEntity>>

    @Query("Select * from userCategorySelected")
    fun getAllNewsCategoryAsLiveData(): LiveData<List<NewsCategoryEntity>>


}
