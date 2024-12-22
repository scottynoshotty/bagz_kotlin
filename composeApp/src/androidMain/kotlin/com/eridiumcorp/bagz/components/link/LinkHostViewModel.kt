package com.eridiumcorp.bagz.components.link

import com.eridiumcorp.bagz.app.services.PlaidService
import com.eridiumcorp.bagz.components.app.AppViewModel
import com.plaid.link.linkTokenConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.inject
import kotlin.getValue

class LinkHostViewModel : AppViewModel() {
    private val plaidService: PlaidService by inject()

    private val _uiState = MutableStateFlow(LinkHostUiState())
    val uiState: StateFlow<LinkHostUiState> = _uiState.asStateFlow()

    init {
        loadLinkToken()
    }

    fun loadLinkToken() {
        launchCatching {
            val linkToken = plaidService.createPlaidLinkToken()
            _uiState.value = _uiState.value.copy(
                loading = false,
                linkTokenConfiguration = linkTokenConfiguration { token = linkToken }
            )
        }
    }

    fun linkInstitution(publicToken: String) {
        launchCatching {
            plaidService.linkInstitution(publicToken)
        }
    }
}