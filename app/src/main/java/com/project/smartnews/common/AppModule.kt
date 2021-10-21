package com.project.smartnews.common

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.smartnews.data.NewsDatabase
import com.project.smartnews.data.dao.ArticleDao
import com.project.smartnews.data.dao.NewsCategoryDao
import com.project.smartnews.data.dao.UserDao
import com.project.smartnews.data.repo.ArticleRepository
import com.project.smartnews.data.repo.BookmarkRepository
import com.project.smartnews.data.repo.UserCategoryRepository
import com.project.smartnews.network.WebService
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
    fun provideUserCategoryRepository(db : NewsDatabase): UserCategoryRepository {
        return UserCategoryRepository(db)
    }

    @Provides
    @Singleton
    fun provideArticleBookmarkRepository(db : NewsDatabase): BookmarkRepository {
        return BookmarkRepository(db)
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
            .fallbackToDestructiveMigration()
            .addCallback(CALLBACK)
            .build()

    private val CALLBACK = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            val INSERT_NEW_USER = "INSERT INTO user_setting (userId, refreshedAt) VALUES (1, 0)"

            val INSERT_NEWS_CATEGORY_1 = "INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (1, \"Top Heading\", 1)"
            //val INSERT_NEWS_CATEGORY_1 = "INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (2, \"General\", 1)"
            val INSERT_NEWS_CATEGORY_2 = " INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (2, \"Entertainment\", 0)"
            val INSERT_NEWS_CATEGORY_3 = " INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (3, \"Health\", 0)"
            val INSERT_NEWS_CATEGORY_4 = " INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (4, \"Science\", 0)"
            val INSERT_NEWS_CATEGORY_5 = " INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (5, \"Sports\", 0)"
            val INSERT_NEWS_CATEGORY_6 = " INSERT INTO userCategorySelected (categoryId, categoryName, categorySelected) VALUES (6, \"Technology\", 0)"

            db.execSQL(INSERT_NEW_USER);
            db.execSQL(INSERT_NEWS_CATEGORY_1)
            db.execSQL(INSERT_NEWS_CATEGORY_2)
            //db.execSQL(INSERT_NEWS_CATEGORY_3)
            //db.execSQL(INSERT_NEWS_CATEGORY_4)
            //db.execSQL(INSERT_NEWS_CATEGORY_5)
            //db.execSQL(INSERT_NEWS_CATEGORY_6)
        }
    }
}