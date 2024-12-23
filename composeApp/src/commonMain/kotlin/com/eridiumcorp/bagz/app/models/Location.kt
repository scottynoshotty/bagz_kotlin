package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractDouble
import com.eridiumcorp.bagz.app.extensions.extractString

data class Location(
    val address: String? = null,
    val city: String? = null,
    val region: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val storeNumber: String? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Location {
            return Location(
                address = map.extractString("address"),
                city = map.extractString("city"),
                region = map.extractString("region"),
                postalCode = map.extractString("postal_code"),
                country = map.extractString("country_code"),
                lat = map.extractDouble("lat"),
                lon = map.extractDouble("lon"),
                storeNumber = map.extractString("store_number")
            )
        }
    }
}
