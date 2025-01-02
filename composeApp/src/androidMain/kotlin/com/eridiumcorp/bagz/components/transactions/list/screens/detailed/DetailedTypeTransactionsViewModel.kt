package com.eridiumcorp.bagz.components.transactions.list.screens.detailed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.eridiumcorp.bagz.app.repositories.TransactionsPagingSource
import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailedTypeTransactionsViewModel(
    private val transactionsRepository: TransactionsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val detailedTypeTransactions = savedStateHandle.toRoute<DetailedTypeTransactions>()
    private val _uiState = MutableStateFlow(
        DetailedTypeTransactionsUiState(
            Pager(PagingConfig(pageSize = 30)) {
                TransactionsPagingSource(
                    transactionsRepository,
                    detailedType = detailedTypeTransactions.detailedType
                )

            })
    )
    val uiState = _uiState.asStateFlow()
}