package com.eridiumcorp.bagz.app.utils

fun formatDouble(value: Double): String {
    val prefix = if (value < 0) "-" else ""
    val absValue = Math.abs(value)
    return prefix + when {
        absValue < 1000 -> String.format("%.0f", absValue)
        absValue < 1000000 -> String.format("%.1fK", absValue / 1000)
        absValue < 1000000000 -> String.format("%.1fM", absValue / 1000000)
        else -> String.format("%.1fB", absValue / 1000000000)
    }
}