package com.eridiumcorp.bagz.components.transactions.list.screens.primary

import androidx.paging.Pager
import com.eridiumcorp.bagz.app.models.Transaction

data class PrimaryTypeTransactionsUiState( val pager: Pager<String, Transaction>)
