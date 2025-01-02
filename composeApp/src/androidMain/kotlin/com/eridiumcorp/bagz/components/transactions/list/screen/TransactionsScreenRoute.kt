package com.eridiumcorp.bagz.components.transactions.list.screen

import com.eridiumcorp.bagz.app.models.DetailedActivityType
import com.eridiumcorp.bagz.app.models.PrimaryActivityType
import kotlinx.serialization.Serializable

@Serializable
data class TransactionsScreenRoute(
    val accountId: String? = null,
    val primaryType: PrimaryActivityType? = null,
    val detailedType: DetailedActivityType? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
