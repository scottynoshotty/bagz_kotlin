package com.eridiumcorp.bagz.components.jarvis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.services.JarvisService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JarvisViewModel(private val jarvisService: JarvisService) : ViewModel() {

    private val _uiState = MutableStateFlow(JarvisUiState())
    val uiState = _uiState.asStateFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            // Add user message to chat history
            _uiState.value = _uiState.value.copy(
                chatHistory = listOf(ChatMessage(message, true)) + _uiState.value.chatHistory
            )
            val response = jarvisService.sendMessage(message)
            if (response != null) {
                // Add Jarvis response to chat history
                _uiState.value = _uiState.value.copy(
                    chatHistory = listOf(ChatMessage(response, false)) + _uiState.value.chatHistory
                )
            }
        }
    }
}