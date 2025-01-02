package com.eridiumcorp.bagz.components.transactions.list.screens.detailed

import com.eridiumcorp.bagz.app.models.DetailedActivityType
import kotlinx.serialization.Serializable

@Serializable
data class DetailedTypeTransactions(val detailedType: DetailedActivityType)