package com.eridiumcorp.bagz.app.services

import com.google.firebase.vertexai.GenerativeModel

class JarvisService(private val generativeModel: GenerativeModel) {

    suspend fun sendMessage(message: String): String? {
        val response = generativeModel.generateContent(message)
        return response.text
    }
}