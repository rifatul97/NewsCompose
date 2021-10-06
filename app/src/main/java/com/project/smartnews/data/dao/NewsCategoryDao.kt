package com.project.smartnews.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsCategoryDao {

    @Query("INSERT INTO userCategorySelected (categoryName) VALUES (:categoryName)")
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryName(categoryName : String)

    @Query("Select categoryName from userCategorySelected")
    fun getSelectedCategory() : Flow<List<String>>

    @Query("DELETE FROM userCategorySelected WHERE categoryName = :categoryName")
    fun removeSelectedCategory(categoryName: String)

}
