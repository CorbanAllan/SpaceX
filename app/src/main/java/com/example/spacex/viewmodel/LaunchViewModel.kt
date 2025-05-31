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

    fun fetchLaunches(isUpcoming: Boolean) {
        viewModelScope.launch {
            try {
                val allLaunches = mutableListOf<Launch>()
                var offset = 0
                var hasNext = true

                while (hasNext) {
                    val response = SpaceDevsClient.api.getSpaceXLaunches(
                        offset = offset,
                        ordering = if (isUpcoming) "net" else "-net",
                        upcoming = isUpcoming
                    )
                    if (response.isSuccessful) {
                        response.body()?.let { launchResponse ->
                            allLaunches += launchResponse.results
                            hasNext = launchResponse.next != null
                            offset += 100
                        }
                    } else {
                        Log.e("LaunchesViewModel", "API call failed: ${response.code()}")
                        hasNext = false
                    }
                }

                _launches.value = allLaunches
            } catch (e: Exception) {
                Log.e("LaunchesViewModel", "Failed to fetch launches", e)
            }
        }
    }
}
