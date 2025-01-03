package com.eridiumcorp.bagz.components.jarvis

data class JarvisUiState(
    val userInput: String = "",
    val chatHistory: List<ChatMessage> = emptyList(),
)

data class ChatMessage(val content: String, val isUser: Boolean)