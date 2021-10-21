package com.project.smartnews.network

import com.project.smartnews.common.Constants.API_KEY
import com.project.smartnews.common.Constants.DEFAULT_COUNTRY
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    //top-headlines?country=us&category=business
    @GET("top-headlines")
    suspend fun getArticlesByCategory(@Query("country") country : String,
                                      @Query("category") categoryName : String,
                                      @Query("apiKey") apiKey: String): Result.Success

    @GET("top-headlines?$DEFAULT_COUNTRY&apiKey=$API_KEY")
    suspend fun getTopHeadings(@Query("country") country : String,
                               @Query("apiKey") apiKey: String): Result.Success

    @GET("everything")
    suspend fun fetchArticlesByText( @Query("q") q: String,
                                     @Query("apiKey") apiKey: String): Result.Success

}

