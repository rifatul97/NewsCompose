package com.project.smartnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_setting")
data class UserEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    val userId: Long,

    @ColumnInfo(name = "refreshedAt", defaultValue = "0")
    val refreshedAt: Long,
)