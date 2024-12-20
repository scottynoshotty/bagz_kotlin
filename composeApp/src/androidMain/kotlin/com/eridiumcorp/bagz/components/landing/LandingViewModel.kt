package com.eridiumcorp.bagz.components.landing

import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.getValue

class LandingViewModel : AppViewModel() {
    private val authService: AuthService by inject()

    private val _uiState = MutableStateFlow(LandingUiState())
    val uiState: StateFlow<LandingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authService.currentUser().collect { user ->
                _uiState.value = LandingUiState(user)
            }
        }
    }
}