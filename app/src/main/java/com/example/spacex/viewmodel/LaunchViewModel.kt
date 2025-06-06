package com.example.spacex.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.Launch
import com.example.spacex.data.network.SpaceDevsClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchViewModel : ViewModel() {
    private val _launches = MutableStateFlow<List<Launch>>(emptyList())
    val launches: StateFlow<List<Launch>> = _launches

    private var offset = 0
    private var isLoadingMore = false
    private var hasNext = true
    private var currentFilterUpcoming = true

    fun fetchInitialLaunches(isUpcoming: Boolean) {
        offset = 0
        hasNext = true
        currentFilterUpcoming = isUpcoming
        _launches.value = emptyList()
        fetchMoreLaunches()
    }

    fun fetchMoreLaunches() {
        if (isLoadingMore || !hasNext) return
        isLoadingMore = true

        viewModelScope.launch {
            try {
                val response = SpaceDevsClient.api.getSpaceXLaunches(
                    offset = offset,
                    ordering = if (currentFilterUpcoming) "net" else "-net",
                    upcoming = currentFilterUpcoming
                )
                if (response.isSuccessful) {
                    response.body()?.let { launchResponse ->
                        _launches.value += launchResponse.results
                        hasNext = launchResponse.next != null
                        offset += 100
                    }
                } else {
                    Log.e("LaunchesViewModel", "API call failed: ${response.code()}")
                    hasNext = false
                }
            } catch (e: Exception) {
                Log.e("LaunchesViewModel", "Failed to fetch launches", e)
                hasNext = false
            } finally {
                isLoadingMore = false
            }
        }
    }
}
