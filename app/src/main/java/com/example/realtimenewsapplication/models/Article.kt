package com.example.realtimenewsapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "NewsArticle")
@Serializable
data class Article(
    @PrimaryKey
    val url: String,
    val author: String?,
    val title: String,
    val description: String?,

    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

