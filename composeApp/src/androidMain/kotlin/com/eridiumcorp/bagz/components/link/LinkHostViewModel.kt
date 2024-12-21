package com.eridiumcorp.bagz.components.link

import com.eridiumcorp.bagz.components.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LinkHostViewModel: AppViewModel() {
    private val _uiState = MutableStateFlow(LinkHostUiState())
    val uiState: StateFlow<LinkHostUiState> = _uiState.asStateFlow()
}