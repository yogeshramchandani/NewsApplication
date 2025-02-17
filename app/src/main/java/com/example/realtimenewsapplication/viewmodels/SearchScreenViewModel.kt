package com.example.realtimenewsapplication.viewmodels

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
class SearchScreenViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // News state
    private val _news = MutableStateFlow<NewsResponse?>(null)
    val news: StateFlow<NewsResponse?> = _news

    // Fetch news based on query
    fun fetchArticles(query: String) {
        // Only perform the fetch if not already loading
        if (_isLoading.value) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response=newsRepository.getSearchedNews(query = query)
                _news.value = response
            } catch (_: Exception) {
                _news.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
