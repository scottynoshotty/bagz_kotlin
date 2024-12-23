package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractString

data class Counterparty(
    val name: String,
    val entityId: String? = null,
    val type: String,
    val website: String? = null,
    val logoUrl: String? = null,
    val confidenceLevel: String? = null
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Counterparty {
            return Counterparty(
                name = map.extractString("name") ?: "",
                type = map.extractString("type") ?: "",
                entityId = map.extractString("entity_id"),
                website = map.extractString("website"),
                logoUrl = map.extractString("logo_url"),
                confidenceLevel = map.extractString("confidence_level")
            )
        }
    }
}
