package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractMap

data class Report(
    val holdingsMap: Map<String, Holdings>,
) {
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