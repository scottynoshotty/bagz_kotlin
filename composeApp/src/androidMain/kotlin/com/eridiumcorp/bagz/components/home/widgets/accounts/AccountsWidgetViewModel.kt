package com.eridiumcorp.bagz.components.home.widgets.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.repositories.AccountsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsWidgetViewModel(accountsRepository: AccountsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountsWidgetUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            accountsRepository.getLinkedAccounts().collect { accounts ->
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    accounts = accounts
                )
            }
        }
    }

}