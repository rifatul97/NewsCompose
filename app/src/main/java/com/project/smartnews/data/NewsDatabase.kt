package com.project.smartnews.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.smartnews.data.dao.ArticleDao
import com.project.smartnews.data.dao.NewsCategoryDao
import com.project.smartnews.data.dao.UserDao
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.model.NewsCategoryEntity
import com.project.smartnews.model.UserEntity

const val DB_VERSION = 3

@Database(
    entities = [
        ArticleEntity::class,
        UserEntity::class,
        NewsCategoryEntity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun ArticleDao() : ArticleDao

    abstract fun UserDao() : UserDao

    abstract fun NewsCategoryDao() : NewsCategoryDao

}