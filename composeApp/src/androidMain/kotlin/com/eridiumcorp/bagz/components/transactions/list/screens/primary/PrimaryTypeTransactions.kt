package com.eridiumcorp.bagz.components.transactions.list.screens.primary

import com.eridiumcorp.bagz.app.models.PrimaryActivityType
import kotlinx.serialization.Serializable

@Serializable
data class PrimaryTypeTransactions(val primaryType: PrimaryActivityType)