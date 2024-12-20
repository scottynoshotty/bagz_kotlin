package com.eridiumcorp.bagz.components.home

import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.inject
import kotlin.getValue

class HomeViewModel : AppViewModel() {
    private val authService: AuthService by inject()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun signOut() {
        launchCatching {
            authService.signOut()
        }
    }
}