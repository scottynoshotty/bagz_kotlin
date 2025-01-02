package com.eridiumcorp.bagz.components.transactions.list.screen

import androidx.paging.Pager
import com.eridiumcorp.bagz.app.models.Transaction

data class TransactionsScreenUiState(val pager: Pager<String, Transaction>)
