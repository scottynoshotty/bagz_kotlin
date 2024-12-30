package com.eridiumcorp.bagz.components.home.widgets.accounts

import com.eridiumcorp.bagz.app.models.Account

data class AccountsWidgetUiState(
    val loading: Boolean = true,
    val accounts: List<Account> = emptyList(),
)
