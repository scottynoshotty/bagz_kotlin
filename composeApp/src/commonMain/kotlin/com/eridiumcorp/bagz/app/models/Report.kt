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
        val weeklyHoldings = mutableListOf<Holdings?>()
        for (i in 0..6) {
            val dayOfYear = (currentDayOfYear - i + 365) % 365  // Wrap around the year
            val holdings = holdingsMap[dayOfYear.toString()]
            if (holdings != null && isWithinLast366Days(holdings.timestampSeconds)) {
                weeklyHoldings.add(holdings)
            } else {
                weeklyHoldings.add(null)
            }
        }
        return weeklyHoldings.reversed() // Reverse to get the correct order
    }

    fun getMonthlyHoldings(): List<Holdings?> {
        val currentDayOfYear = getCurrentDayOfYear()
        val monthlyHoldings = mutableListOf<Holdings?>()
        for (i in 0..6) {
            val dayOfYear = (currentDayOfYear - i * 5 + 365) % 365 // Wrap around the year
            val holdings = holdingsMap[dayOfYear.toString()]
            if (holdings != null && isWithinLast366Days(holdings.timestampSeconds)) {
                monthlyHoldings.add(holdings)
            } else {
                monthlyHoldings.add(null)
            }
        }
        return monthlyHoldings.reversed() // Reverse to get the correct order
    }

    fun getYearlyHoldings(): List<Holdings?> {
        val currentDayOfYear = getCurrentDayOfYear()
        val yearlyHoldings = (0..6).map { index ->  // Sample every 52 days, excluding the last day
            val dayOfYear = currentDayOfYear - (index * 52)
            val adjustedDayOfYear = (dayOfYear).takeIf { it > 0 } ?: (dayOfYear + 365)
            holdingsMap[adjustedDayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }.toMutableList()
        yearlyHoldings.reverse()
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