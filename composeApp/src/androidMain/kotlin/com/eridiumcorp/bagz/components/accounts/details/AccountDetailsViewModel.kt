package com.eridiumcorp.bagz.components.accounts.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.eridiumcorp.bagz.app.repositories.AccountsRepository
import com.eridiumcorp.bagz.app.repositories.TransactionsPagingSource
import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountDetailsViewModel(
    private val accountsRepository: AccountsRepository,
    private val transactionsRepository: TransactionsRepository,
    savedStateHandle: SavedStateHandle,
) :
    AppViewModel() {

    private val accountDetails = savedStateHandle.toRoute<AccountDetails>()
    private val _uiState = MutableStateFlow(AccountDetailsUiState())
    val uiState: StateFlow<AccountDetailsUiState> = _uiState.asStateFlow()
    val transactions = Pager(PagingConfig(pageSize = 30)) {
        TransactionsPagingSource(transactionsRepository, accountDetails.accountId)
    }

    init {
        launchCatching {
            val account = accountsRepository.getAccountById(accountDetails.accountId)
            _uiState.value = _uiState.value.copy(loading = false, account = account)
        }
    }
}