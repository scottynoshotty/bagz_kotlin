package com.eridiumcorp.bagz.app.models

import kotlinx.datetime.TimeZone

data class Country(val name: String, val zone: TimeZone)

fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo")),
    Country("France", TimeZone.of("Europe/Paris")),
    Country("Mexico", TimeZone.of("America/Mexico_City")),
    Country("Indonesia", TimeZone.of("Asia/Jakarta")),
    Country("Egypt", TimeZone.of("Africa/Cairo")),
)