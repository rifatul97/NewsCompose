package com.project.smartnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userCategorySelected")
data class NewsCategoryEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryId")
    val categoryId: Int,

    @ColumnInfo(name = "categoryName")
    val categoryName: String,

    @ColumnInfo(name = "categorySelected")
    val categorySelected: Boolean,
)