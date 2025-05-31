package com.example.spacex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.LauncherConfig
import com.example.spacex.data.network.SpaceDevsClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RocketsViewModel : ViewModel() {

    private val _rockets = MutableStateFlow<List<LauncherConfig>>(emptyList())
    val rockets: StateFlow<List<LauncherConfig>> = _rockets

    init {
        fetchRockets()
    }

    private fun fetchRockets() {
        viewModelScope.launch {
            try {
                val response = SpaceDevsClient.api.getRockets()
                _rockets.value = response.results
            } catch (e: Exception) {
                Log.e("RocketsViewModel", "Failed to fetch rockets", e)
            }
        }
    }
}