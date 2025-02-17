package com.example.realtimenewsapplication.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.example.realtimenewsapplication.models.Article
import com.example.realtimenewsapplication.viewmodels.FavouriteArticleViewModel
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedNewsScreen(
    backStackEntry: NavBackStackEntry,
    viewModel: FavouriteArticleViewModel = hiltViewModel() // Use Hilt for DI
) {
    val articleJson = backStackEntry.arguments?.getString("article")

    if (articleJson == null) {
        Text(text = "Article not found")
        return
    }

    val article = try {
        Json.decodeFromString<Article>(articleJson)
    } catch (_: Exception) {
        Text(text = "Error decoding article")
        return
    }

    // Collect favorite articles from ViewModel
    val favoriteArticles by viewModel.favoriteArticles.collectAsState(initial = emptyList())

    // Check if the article is in the favorite list using its `url`
    val isFavourite = favoriteArticles.any { it.url == article.url }

    Scaffold(
        content = { padding ->
            WebViewScreen(article.url, modifier = Modifier.padding(padding)) // Assuming WebViewScreen is implemented
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleFavorite(article) }
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        }
    )
}
