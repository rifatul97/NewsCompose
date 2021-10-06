package com.project.smartnews.network

import com.project.smartnews.common.Constants.API_KEY
import com.project.smartnews.common.Constants.DEFAULT_COUNTRY
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.project.smartnews.network.Result

interface WebService {

    //top-headlines?country=de&category=business
    @GET("top-headlines?${DEFAULT_COUNTRY}&category={name}&apiKey=$API_KEY")
    suspend fun getArticlesByCategory(@Path("name") categoryName : String): Result.Success

    @GET("top-headlines?$DEFAULT_COUNTRY&apiKey=$API_KEY")
    suspend fun getTopHeadings(): Result.Success

}

