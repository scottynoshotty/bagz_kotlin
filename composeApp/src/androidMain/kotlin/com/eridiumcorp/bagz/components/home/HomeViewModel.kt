package com.eridiumcorp.bagz.components.home

import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.app.services.PlaidService
import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.inject
import kotlin.getValue

class HomeViewModel : AppViewModel() {
    private val authService: AuthService by inject()
    private val plaidService: PlaidService by inject()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun launchLink() {
        launchCatching {
            val token = plaidService.createPlaidLinkToken()
            if (token == null) {
                println("123456789 Got a null link token")
            } else {
                println("123456789 Link token: $token")
            }
        }
    }

    fun signOut() {
        launchCatching {
            authService.signOut()
        }
    }
}