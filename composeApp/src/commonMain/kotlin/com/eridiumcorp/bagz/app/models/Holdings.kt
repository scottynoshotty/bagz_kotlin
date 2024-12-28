package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractInt
import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.extensions.extractString

data class Holdings(
    val timestampSeconds: Int?,
    val userId: String?,
    val holdingsData: HoldingsData,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Holdings {
            return Holdings(
                timestampSeconds = map.extractInt("timestamp_seconds"),
                userId = map.extractString("user_id"),
                holdingsData = HoldingsData.fromMap(map.extractMap("holdings_data") ?: emptyMap())
            )
        }
    }
}
