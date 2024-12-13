package com.eridiumcorp.bagz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform