package com.example.spacex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.Rocket
import com.example.spacex.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RocketViewModel : ViewModel() {

    private val _rockets = MutableStateFlow<List<Rocket>>(emptyList())
    val rockets: StateFlow<List<Rocket>> = _rockets

    init {
        fetchRockets()
    }

    private fun fetchRockets() {
        viewModelScope.launch {
            try {
                val rocketList = RetrofitInstance.api.getRockets()
                _rockets.value = rocketList
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}