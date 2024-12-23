package com.eridiumcorp.bagz.components.accounts.details

import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountDetailsViewModel(private val transactionsRepository: TransactionsRepository) :
    AppViewModel() {

    private val _uiState = MutableStateFlow(AccountDetailsUiState(loading = true))
    val uiState: StateFlow<AccountDetailsUiState> = _uiState.asStateFlow()

    init {
        launchCatching {
            transactionsRepository.getTransactions()
            _uiState.value = uiState.value.copy(loading = false)
        }
    }
}