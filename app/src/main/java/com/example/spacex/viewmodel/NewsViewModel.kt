package com.example.spacex.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.Article
import com.example.spacex.data.network.NewsApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _newsArticles = MutableStateFlow<List<Article>>(emptyList())
    val newsArticles: StateFlow<List<Article>> = _newsArticles

    init {
        fetchSpaceXNews("428e192f3e1e4eb481208adf4cd72b02")
    }

    private fun fetchSpaceXNews(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = NewsApiClient.apiService.getSpaceXNews(apiKey = apiKey)
                _newsArticles.value = response.articles
                    .filter { !it.imageUrl.isNullOrEmpty() }  // Keep only articles with images
                    .sortedByDescending { it.publishedAt }  // Sort by published date, most recent first
                    .take(12)  // Take only the first 10
            } catch (e: Exception) {
                // Handle error here
            }
        }
    }

}