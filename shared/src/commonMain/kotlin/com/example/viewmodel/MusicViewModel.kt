package com.example.viewmodel

import com.example.model.Track
import com.example.network.MusicApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicViewModel {

    private val api = MusicApi()

    // Background coroutine scope (for shared KMP)
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadTracks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val result = api.getTracks()
                _tracks.value = result

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
