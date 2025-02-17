package com.example.realtimenewsapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realtimenewsapplication.models.Article
import com.example.realtimenewsapplication.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteArticleViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val favoriteArticles: Flow<List<Article>> = repository.getFavouriteArticles()

    private val _isLoading = MutableStateFlow(false) // Track loading state
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null) // Track error message
    val error: StateFlow<String?> get() = _error

    fun toggleFavorite(article: Article) {
        // Show loading state
        _isLoading.value = true
        _error.value = null  // Clear any previous error

        viewModelScope.launch {
            try {
                // Toggle favorite in repository
                repository.toggleFavorite(article)
            } catch (e: Exception) {
                // Handle error and update the error state
                _error.value = "Failed to toggle favorite: ${e.message}"
            } finally {
                // Hide loading state after operation completes
                _isLoading.value = false
            }
        }
    }
}
