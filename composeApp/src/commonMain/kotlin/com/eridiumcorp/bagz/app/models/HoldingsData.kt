package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractDouble

data class HoldingsData(
    val net: Double?,
    val cash: Double?,
    val investments: Double?,
    val debt: Double?,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): HoldingsData {
            return HoldingsData(
                net = map.extractDouble("net"),
                cash = map.extractDouble("cash"),
                investments = map.extractDouble("investments"),
                debt = map.extractDouble("debt")
            )
        }
    }
}
