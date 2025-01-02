package com.eridiumcorp.bagz.app.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

fun getCurrentDayOfYear(): Int {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val localDate = localDateTime.date
    return localDate.dayOfYear
}

fun isWithinLast366Days(timestampSeconds: Int?): Boolean {
    if (timestampSeconds == null) return false
    val timestampInstant = Instant.fromEpochSeconds(timestampSeconds.toLong())
    val now = Clock.System.now()
    val oneYearAgo = now - 366.days
    return timestampInstant >= oneYearAgo
}

fun getAbbreviatedMonthName(timestampSeconds: Int): String {
    val timestampInstant = Instant.fromEpochSeconds(timestampSeconds.toLong())
    val localDateTime = timestampInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val month = localDateTime.month
    return month.name.substring(0, 3)
}

fun getMonthDayString(timestampSeconds: Int): String {
    val timestampInstant = Instant.fromEpochSeconds(timestampSeconds.toLong())
    val localDateTime = timestampInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val month = localDateTime.month.name.substring(0, 3)
    val day = localDateTime.dayOfMonth
    return "$month $day"
}

fun getDayOfWeekString(timestampSeconds: Int): String {
    val timestampInstant = Instant.fromEpochSeconds(timestampSeconds.toLong())
    val localDateTime = timestampInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dayOfWeek = localDateTime.dayOfWeek
    return dayOfWeek.name.substring(0, 3)
}

fun getMonthlyActivityStartDate(monthActivityKey: String): String {
    return "${monthActivityKey}-01"
}


fun getMonthlyActivityEndDate(monthActivityKey: String): String {
    return "${monthActivityKey}-31"
}

fun currentMonthActivityKey(): String {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val year = localDateTime.year
    val month = localDateTime.month.value
    return String.format("%04d-%02d", year, month)
}

fun getCurrentMonthName(): String {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.month.name
}