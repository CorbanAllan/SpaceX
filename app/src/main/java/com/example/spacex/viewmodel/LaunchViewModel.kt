package com.example.spacex.viewmodel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.model.Launch
import com.example.spacex.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LaunchViewModel : ViewModel() {
    private val _launches = mutableStateListOf<Launch>()
    val launches: List<Launch> get() = _launches

    init {
        fetchLaunches()
    }

    private fun fetchLaunches() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getLaunches()
                _launches.addAll(response.sortedByDescending { parseDate(it.date_utc) })
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    private fun parseDate(dateString: String): Long {
        val formatter = DateTimeFormatter.ISO_DATE_TIME // Adjust as necessary
        return ZonedDateTime.parse(dateString, formatter).toEpochSecond() * 1000 // Convert to milliseconds
    }

}