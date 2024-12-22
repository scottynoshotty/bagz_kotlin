package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractBoolean
import com.eridiumcorp.bagz.app.extensions.extractString
import com.eridiumcorp.bagz.app.extensions.extractStringList

data class Institution(
    val institutionId: String,
    val name: String,
    val products: List<String>,
    val countryCodes: List<String>,
    val url: String? = null,
    val primaryColor: String? = null,
    val logo: String? = null,
    val routingNumbers: List<String>,
    val dtcNumbers: List<String>,
    val oauth: Boolean
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Institution {
            return Institution(
                institutionId = map.extractString("institution_id") ?: "",
                name = map.extractString("name") ?: "",
                products = map.extractStringList("products") ?: emptyList(),
                countryCodes = map.extractStringList("country_codes") ?: emptyList(),
                url = map.extractString("url"),
                primaryColor = map.extractString("primary_color"),
                logo = map.extractString("logo"),
                routingNumbers = map.extractStringList("routing_numbers") ?: emptyList(),
                dtcNumbers = map.extractStringList("dtc_numbers") ?: emptyList(),
                oauth = map.extractBoolean("oauth") == true
            )
        }
    }
}