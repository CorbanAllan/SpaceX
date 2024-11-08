package com.example.spacex.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.Launch
import com.example.spacex.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LaunchViewModel : ViewModel() {
    // Use StateFlow for observing the list of launches in Compose
    private val _launches = MutableStateFlow<List<Launch>>(emptyList()) // Initially empty list
    val launches: StateFlow<List<Launch>> = _launches // Expose as StateFlow to be observed

    init {
        fetchLaunches()
    }

    private fun fetchLaunches() {
        viewModelScope.launch {
            try {
                // Fetching 10 most recent launches from the SpaceX API
                val response = RetrofitInstance.api.getLaunches()

                val sortedLaunches = response.sortedByDescending { it.date_utc }
                val limitedLaunches = sortedLaunches.take(10)
                // Updating the state with the fetched launches
                _launches.value = limitedLaunches
            } catch (e: Exception) {
                // Handle any errors (e.g., log it or show a message to the user)
                Log.e("LaunchViewModel", "Error fetching launches", e)
            }
        }
    }
}