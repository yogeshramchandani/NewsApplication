package com.example.realtimenewsapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realtimenewsapplication.models.NewsResponse
import com.example.realtimenewsapplication.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _news = MutableStateFlow<NewsResponse?>(null)
    val news: StateFlow<NewsResponse?> = _news
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentPage = 1
    private var pageSize=10
        init {
            fetchArticles()
        }
    fun fetchArticles() {
        if (_isLoading.value) return

        _isLoading.value = true
        viewModelScope.launch {
            val newArticles = newsRepository.getNews(currentPage)
            if (newArticles != null) {
                _news.value = _news.value?.copy(
                    articles = _news.value!!.articles + newArticles.articles
                ) ?: newArticles
                currentPage++ // Move to the next page
            }
            _isLoading.value = false
        }
    }
}
