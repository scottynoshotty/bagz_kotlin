package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.utils.getCurrentDayOfYear
import com.eridiumcorp.bagz.app.utils.isWithinLast366Days

data class Report(
    val holdingsMap: Map<String, Holdings>,
) {
    fun getCurrentDayHoldings(): Holdings? {
        val currentDayOfYear = getCurrentDayOfYear()
        val holdings = holdingsMap[currentDayOfYear.toString()]
        return holdings?.takeIf { isWithinLast366Days(it.timestampSeconds) }
    }

    fun getWeeklyHoldings(): List<Holdings?> {
        val currentDayOfYear = getCurrentDayOfYear()
        val weeklyHoldings = (currentDayOfYear - 6..currentDayOfYear - 1).map { dayOfYear ->
            holdingsMap[dayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }.toMutableList()
        // Add current day holdings as the last element
        weeklyHoldings.add(getCurrentDayHoldings())
        return weeklyHoldings
    }

    fun getMonthlyHoldings(): List<Holdings?> {
        val currentDayOfYear = getCurrentDayOfYear()
        val monthlyHoldings = (0..5).map { index ->  // Sample every 5 days, excluding the last day
            val dayOfYear = currentDayOfYear - (index * 5)
            holdingsMap[dayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }.toMutableList()
        // Add current day holdings as the last element
        monthlyHoldings.add(getCurrentDayHoldings())
        return monthlyHoldings
    }

    fun getYearlyHoldings(): List<Holdings?> {
        val currentDayOfYear = getCurrentDayOfYear()
        val yearlyHoldings = (0..5).map { index ->  // Sample every 52 days, excluding the last day
            val dayOfYear = currentDayOfYear - (index * 52)
            val adjustedDayOfYear = (dayOfYear).takeIf { it > 0 } ?: (dayOfYear + 365)
            holdingsMap[adjustedDayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }.toMutableList()
        // Add current day holdings as the last element
        yearlyHoldings.add(getCurrentDayHoldings())
        return yearlyHoldings
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): Report {
            val holdingsMap: Map<String, Any?>? = map.extractMap("holdings")
            if (holdingsMap == null) {
                return Report(emptyMap())
            }

            return Report(holdingsMap.mapValues { (_, value) ->
                @Suppress("UNCHECKED_CAST")
                val holdingsData = value as? Map<String, Any?> ?: emptyMap()
                Holdings.fromMap(holdingsData)
            })
        }
    }
}