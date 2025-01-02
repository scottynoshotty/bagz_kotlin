package com.eridiumcorp.bagz.components.transactions.list.screens.primary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.eridiumcorp.bagz.app.repositories.TransactionsByPrimaryTypePagingSource
import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrimaryTypeTransactionsViewModel(
    private val transactionsRepository: TransactionsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val primaryTypeTransactions = savedStateHandle.toRoute<PrimaryTypeTransactions>()
    private val _uiState = MutableStateFlow(
        PrimaryTypeTransactionsUiState(
            Pager(PagingConfig(pageSize = 30)) {
                TransactionsByPrimaryTypePagingSource(
                    transactionsRepository,
                    primaryTypeTransactions.primaryType
                )

            })
    )
    val uiState = _uiState.asStateFlow()
}