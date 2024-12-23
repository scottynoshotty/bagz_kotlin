package com.eridiumcorp.bagz.components.accounts.details

import com.eridiumcorp.bagz.app.models.Account

data class AccountDetailsUiState(val loading: Boolean = true, val account: Account? = null)
