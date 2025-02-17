package com.example.realtimenewsapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realtimenewsapplication.viewmodels.FavouriteArticleViewModel
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteArticlesScreen(viewModel: FavouriteArticleViewModel = hiltViewModel(),navController: NavController) {
    var favoriteArticles = viewModel.favoriteArticles.collectAsState(initial = emptyList())

            if (favoriteArticles.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No favorite articles yet.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteArticles.value) { article ->
                        val decodedTitle = java.net.URLDecoder.decode(article.title, StandardCharsets.UTF_8.toString())

                        NewsList(article = article,navController)
                    }
                }
            }
        }


