package com.eridiumcorp.bagz.app.services

import com.google.firebase.vertexai.Chat

class JarvisService(private val chat: Chat) {

    suspend fun sendMessage(message: String): String? {
        val response = chat.sendMessage(message)
        return response.text
    }
}