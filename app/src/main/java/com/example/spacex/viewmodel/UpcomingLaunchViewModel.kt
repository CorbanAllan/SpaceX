package com.example.spacex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.UpcomingLaunch
import com.example.spacex.data.network.SpaceDevsClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpcomingLaunchViewModel : ViewModel() {

    private val _launches = MutableStateFlow<List<UpcomingLaunch>>(emptyList())
    val launches: StateFlow<List<UpcomingLaunch>> = _launches

    init {
        fetchLaunches()
    }

    private fun fetchLaunches() {
        viewModelScope.launch {
            try {
                val response = SpaceDevsClient.api.getUpcomingSpaceXLaunch()
                _launches.value = response.results
            } catch (e: Exception) {
                Log.e("LaunchViewModel", "Error fetching launches", e)
            }
        }
    }
}