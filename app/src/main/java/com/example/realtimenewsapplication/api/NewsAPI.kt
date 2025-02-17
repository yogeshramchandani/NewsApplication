package com.example.realtimenewsapplication.api

import com.example.realtimenewsapplication.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NewsAPI {

    @GET("/v2/top-headlines")
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String="us",
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey:String="adbe53f9d9f64c35adfeb58580ae2b84",
        @Query("pageSize")
        pageSize: Int=10
    ): Response<NewsResponse>

    @GET("/v2/everything")
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    suspend fun getSearchNews(
        @Query("apiKey")
        apiKey:String="adbe53f9d9f64c35adfeb58580ae2b84",
        @Query("q")
        query: String=""
    ): Response<NewsResponse>
}