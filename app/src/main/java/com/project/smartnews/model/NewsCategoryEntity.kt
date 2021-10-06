package com.project.smartnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userCategorySelected")
data class NewsCategoryEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    val categoryId: Long,

    @ColumnInfo(name = "categoryName")
    val categoryName: String
)