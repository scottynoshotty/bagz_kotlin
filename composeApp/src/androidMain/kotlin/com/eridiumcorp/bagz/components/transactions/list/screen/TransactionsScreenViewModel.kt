package com.eridiumcorp.bagz.components.transactions.list.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.eridiumcorp.bagz.app.repositories.TransactionsPagingSource
import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TransactionsScreenViewModel(
    private val transactionsRepository: TransactionsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionsScreenRoute = savedStateHandle.toRoute<TransactionsScreenRoute>()
    private val _uiState = MutableStateFlow(
        TransactionsScreenUiState(
            Pager(PagingConfig(pageSize = 30)) {
                TransactionsPagingSource(
                    transactionsRepository,
                    accountId = transactionsScreenRoute.accountId,
                    primaryType = transactionsScreenRoute.primaryType,
                    detailedType = transactionsScreenRoute.detailedType,
                    startDate = transactionsScreenRoute.startDate,
                    endDate = transactionsScreenRoute.endDate,
                )
            }
        )
    )
    val uiState = _uiState.asStateFlow()
}