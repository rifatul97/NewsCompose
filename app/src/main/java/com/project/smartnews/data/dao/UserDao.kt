package com.project.smartnews.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.smartnews.model.UserEntity

@Dao
interface UserDao {

    @Query("Select refreshedAt from user_setting WHERE userId = 1")
    fun getLastRefreshedTime() : Long

    @Query("Update user_setting SET refreshedAt = :currentDate WHERE userId = 1")
    fun insertRefreshTime(currentDate: Long)

    @Query("Select * from user_setting")
    fun selectAll(): List<UserEntity>

    @Insert
    fun insert(userEntity: UserEntity)
}