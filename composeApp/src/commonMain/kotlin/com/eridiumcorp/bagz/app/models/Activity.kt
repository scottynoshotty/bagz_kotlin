package com.eridiumcorp.bagz.app.models

import androidx.compose.ui.graphics.Color
import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.utils.currentMonthActivityKey

data class Activity(
    val activityData: List<ActivitySummary>?,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Activity {
            val currentActivityKey = currentMonthActivityKey()
            println("123456789: Current activity key: $currentActivityKey")
            val currentActivity = map.extractMap(currentActivityKey)
            if (currentActivity == null) {
                return Activity(null)
            }
            val activitySummaryList = currentActivity
                .entries.mapNotNull { entry ->
                    ActivitySummary.fromEntry(entry)
                }
            return Activity(activitySummaryList)
        }
    }
}

data class ActivitySummary(val type: ActivityType, val amount: Double) {

    companion object {
        fun fromEntry(entry: Map.Entry<String, Any?>): ActivitySummary? {
            val amount = entry.value
            if (amount is Double) {
                if (amount < 1) {
                    return null
                }
                val activityType = ActivityType.fromString(entry.key)
                if (activityType != null) {
                    println("123456789 --------------------------------------------")
                    println("123456789 ${activityType.name}")
                    println("123456789 ${kotlin.math.abs(amount)}")
                    println("123456789 --------------------------------------------")
                    return ActivitySummary(activityType, kotlin.math.abs(amount))
                }
            }
            return null
        }
    }
}

enum class ActivityType(val color: Color) {
    INCOME(Color(0xFF00C853)),        // Green
    TRANSFER_IN(Color(0xFF00B0FF)),    // Blue
    LOAN_PAYMENTS(Color(0xFFFFD600)), // Yellow
    BANK_FEES(Color(0xFFD50000)),     // Red
    ENTERTAINMENT(Color(0xFFAA00FF)),  // Purple
    FOOD_AND_DRINK(Color(0xFF00BCD4)),  // Cyan
    GENERAL_MERCHANDISE(Color(0xFF64B5F6)),  // Light blue
    HOME_IMPROVEMENT(Color(0xFFFF9800)),  // Orange
    MEDICAL(Color(0xFFE91E63)),       // Pink
    PERSONAL_CARE(Color(0xFF9C27B0)),  // Deep purple
    GENERAL_SERVICES(Color(0xFF009688)),  // Teal
    GOVERNMENT_AND_NON_PROFIT(Color(0xFFF44336)),  // Red
    TRANSPORTATION(Color(0xFF2196F3)),  // Blue
    TRAVEL(Color(0xFF4CAF50)),       // Green
    RENT_AND_UTILITIES(Color(0xFFFFC107)); // Amber

    companion object {
        fun fromString(type: String): ActivityType? {
            return entries.find { it.name == type }
        }
    }
}
