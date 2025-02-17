package com.example.realtimenewsapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.realtimenewsapplication.R
import com.example.realtimenewsapplication.models.Article
import com.example.realtimenewsapplication.models.NewsResponse
import com.example.realtimenewsapplication.viewmodels.HomeScreenViewModel
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val news: State<NewsResponse?> = homeScreenViewModel.news.collectAsState()
    val isLoading = homeScreenViewModel.isLoading.collectAsState()
    val refreshing = remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()

    LaunchedEffect(refreshing.value) {
        if (refreshing.value) {
            homeScreenViewModel.fetchArticles()
            refreshing.value = false
        }
    }

    if (news.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading News or No Data", style = MaterialTheme.typography.bodyLarge)
        }
    } else if (news.value!!.articles.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No Articles Found", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        PullToRefreshBox(
            isRefreshing = refreshing.value,
            state = state,
            onRefresh = {
                refreshing.value = true
            }
        ) {
            LazyColumn {
                items(news.value!!.articles) { newsItem ->
                    NewsList(newsItem, navController)
                }
                // Show loading indicator at the bottom while fetching new data
                item {
                    if (isLoading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            // Detect when the user reaches the end of the list
            LaunchedEffect(news.value?.articles?.size) {
                snapshotFlow { news.value?.articles?.size ?: 0 }
                    .collect { size ->
                        if (size > 0 && !isLoading.value) {
                            homeScreenViewModel.fetchArticles()
                        }
                    }
            }
        }
    }
}
    @Composable
fun NewsList(article: Article,navController: NavController) {
        val articleJson = URLEncoder.encode(Json.encodeToString(article), StandardCharsets.UTF_8.toString())
        Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = {
                navController.navigate("detailScreen/$articleJson")
            })
    ) {
            val decodedTitle = URLDecoder.decode(article.title, StandardCharsets.UTF_8.toString())
            Row(horizontalArrangement =Arrangement.Center, modifier = Modifier.wrapContentHeight()){
            AsyncImage(
                model = article.urlToImage?:R.drawable.newsplaceholder,
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(90.dp),
                contentScale = ContentScale.FillHeight
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = decodedTitle,
                    modifier = Modifier.padding(16.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}


