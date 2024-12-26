package com.eridiumcorp.bagz.components.home

import com.eridiumcorp.bagz.app.repositories.ReportsRepository
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(val authService: AuthService, val reportsRepository: ReportsRepository) :
    AppViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launchCatching {
            reportsRepository.getReport().collect { report ->
                _uiState.value = _uiState.value.copy(report = report, loading = false)
            }
        }
    }

    fun signOut() {
        launchCatching {
            authService.signOut()
        }
    }
}