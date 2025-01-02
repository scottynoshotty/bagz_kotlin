package com.eridiumcorp.bagz.components.transactions.list.screens.detailed

import androidx.paging.Pager
import com.eridiumcorp.bagz.app.models.Transaction

data class DetailedTypeTransactionsUiState(val pager: Pager<String, Transaction>)
