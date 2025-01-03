package com.eridiumcorp.bagz.app.services

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class JarvisService {

    suspend fun sendMessage(message: String): String? {
        delay(2000)
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val formattedTime = currentTime.time.toString().substring(0, 5) // Extract HH:MM
        return "This is a response to a chat message $formattedTime"
    }
}