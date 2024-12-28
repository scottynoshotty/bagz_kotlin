package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.utils.getDaysOfYear
import com.eridiumcorp.bagz.app.utils.isWithinLast366Days

data class Report(
    val holdingsMap: Map<String, Holdings>,
) {
    fun getCurrentDayHoldings(): Holdings? {
        val currentDayOfYear = getDaysOfYear()
        val holdings = holdingsMap[currentDayOfYear.toString()]
        return holdings?.takeIf { isWithinLast366Days(it.timestampSeconds) }
    }

    fun getLast7DaysHoldings(): List<Holdings?> {
        val currentDayOfYear = getDaysOfYear()
        return (currentDayOfYear - 6..currentDayOfYear).map { dayOfYear ->
            holdingsMap[dayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }
    }

    fun getLast30DaysHoldings(): List<Holdings?> {
        val currentDayOfYear = getDaysOfYear()
        return (currentDayOfYear - 29..currentDayOfYear).map { dayOfYear ->
            holdingsMap[dayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }
    }

    fun getLast365DaysHoldings(): List<Holdings?> {
        val currentDayOfYear = getDaysOfYear()
        return (1..365).map { dayOfYear ->
            val adjustedDayOfYear =
                (currentDayOfYear - 365 + dayOfYear).takeIf { it > 0 } ?: (dayOfYear + 365)
            holdingsMap[adjustedDayOfYear.toString()]?.takeIf { isWithinLast366Days(it.timestampSeconds) }
        }
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