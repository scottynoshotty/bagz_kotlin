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
            val accountData = map.extractMap("account_data") ?: emptyMap()
            return Account(
                accountId = map.extractString("account_id") ?: "",
                balances = Balances.fromMap(accountData.extractMap("balances") ?: emptyMap()),
                mask = accountData.extractString("mask"),
                name = accountData.extractString("name") ?: "",
                officialName = accountData.extractString("official_name"),
                accountType = accountData.extractString("type") ?: "Other",
                institution = Institution.fromMap(map.extractMap("institution_data") ?: emptyMap())
            )
        }
    }
}
