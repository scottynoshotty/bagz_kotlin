package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractString

data class PersonalFinanceCategory(
    val primary: String? = null,
    val detailed: String? = null,
    val confidenceLevel: String? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): PersonalFinanceCategory {
            return PersonalFinanceCategory(
                primary = map.extractString("primary"),
                detailed = map.extractString("detailed"),
                confidenceLevel = map.extractString("confidence_level")
            )
        }
    }
}
