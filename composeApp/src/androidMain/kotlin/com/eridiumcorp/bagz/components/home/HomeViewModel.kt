package com.eridiumcorp.bagz.components.home

import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.repositories.AccountsRepository
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(val authService: AuthService, val accountsRepository: AccountsRepository) :
    AppViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            accountsRepository.getLinkedAccounts().collect { accounts ->
                _uiState.value = _uiState.value.copy(accounts = accounts, loading = false)
            }
        }
    }

    fun signOut() {
        launchCatching {
            authService.signOut()
        }
    }
}