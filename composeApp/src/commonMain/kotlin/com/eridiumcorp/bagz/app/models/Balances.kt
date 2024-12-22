package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractDouble
import com.eridiumcorp.bagz.app.extensions.extractString

data class Balances(
    val available: Double? = null,
    val current: Double? = null,
    val limit: Double? = null,
    val isoCurrencyCode: String? = null,
    val unofficialCurrencyCode: String? = null
) {

    companion object {
        fun fromMap(map: Map<String, Any?>): Balances {
            return Balances(
                available = map.extractDouble("available"),
                current = map.extractDouble("current"),
                limit = map.extractDouble("limit"),
                isoCurrencyCode = map.extractString("iso_currency_code"),
                unofficialCurrencyCode = map.extractString("unofficial_currency_code")
            )
        }
    }
}