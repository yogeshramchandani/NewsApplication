package com.example.realtimenewsapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realtimenewsapplication.viewmodels.SearchScreenViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
    val news = searchScreenViewModel.news.collectAsState()
    val isLoading = searchScreenViewModel.isLoading.collectAsState().value
    var text = remember { mutableStateOf("") }

    Column {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .height(56.dp)
                .fillMaxWidth()
                .wrapContentSize()
        ) {
            TextField(
                maxLines = 1,
                trailingIcon = {
                    IconButton(onClick = { searchScreenViewModel.fetchArticles(query = text.value) }) {
                        Icon(
                            Icons.Filled.Search, contentDescription = "", modifier = Modifier.wrapContentSize()
                        )
                    }
                },

                modifier = Modifier.fillMaxWidth(),
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Search") },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        searchScreenViewModel.fetchArticles(query = text.value)
                    }

                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done // This allows the "Enter" key to trigger the done action
                )
            )
        }



        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Display news articles or handle case where no articles are found
        news.value?.articles?.let { articles ->
            LazyColumn {
                items(articles) { newsItem ->
                    NewsList(newsItem, navController)
                }
            }
        } ?: run {
            // Show a message if no news or null data
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No news found")
            }
        }
    }
}
