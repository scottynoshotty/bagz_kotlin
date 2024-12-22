package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.extensions.extractString

data class Account(
    val accountId: String,
    val balances: Balances,
    val mask: String? = null,
    val name: String,
    val officialName: String? = null,
    val accountType: String,
    val institution: Institution,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Account {
            return Account(
                accountId = map.extractString("account_id") ?: "",
                balances = Balances.fromMap(map.extractMap("balances") ?: emptyMap()),
                mask = map.extractString("mask"),
                name = map.extractString("name") ?: "",
                officialName = map.extractString("official_name"),
                accountType = map.extractString("type") ?: "Other",
                institution = Institution.fromMap(map.extractMap("institution") ?: emptyMap())
            )
        }
    }
}
