package com.example.realtimenewsapplication.models


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
