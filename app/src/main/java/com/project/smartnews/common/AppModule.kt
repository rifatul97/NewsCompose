package com.project.smartnews.common

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.data.dao.ArticleDao
import com.project.smartnews.network.WebService
import com.project.smartnews.data.repo.ArticleRepository
import com.project.smartnews.data.dao.NewsCategoryDao
import com.project.smartnews.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): WebService {
        println("webservice is produced with SUCCESS")
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleDao(db: NewsDatabase): ArticleDao {
        return db.ArticleDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: NewsDatabase): UserDao {
        return db.UserDao()
    }

    @Provides
    @Singleton
    fun provideArticleRepository(api: WebService, db : NewsDatabase): ArticleRepository {
        return ArticleRepository(api, db)
    }


    @Provides
    @Singleton
    fun provideNewsCatagoryDao(db : NewsDatabase): NewsCategoryDao {
        return db.NewsCategoryDao()
    }


    @Provides
    @Singleton
    fun provideDatabase(app: Application) : NewsDatabase =
        Room.databaseBuilder(app, NewsDatabase::class.java, "hypernews_database")
            //.allowMainThreadQueries()
            //.fallbackToDestructiveMigration()
            //.addCallback(CALLBACK)
            .build()

    private val CALLBACK = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            db.execSQL("INSERT INTO user_setting (userId, refreshedAt) VALUES (1, 0)")
        }
    }
}