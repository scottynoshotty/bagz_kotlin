package com.eridiumcorp.bagz.components.home

import com.eridiumcorp.bagz.app.models.Account
import com.eridiumcorp.bagz.app.models.User

data class HomeUiState(
    val loading: Boolean = true,
    val user: User? = null,
    val accounts: List<Account> = emptyList(),
)
