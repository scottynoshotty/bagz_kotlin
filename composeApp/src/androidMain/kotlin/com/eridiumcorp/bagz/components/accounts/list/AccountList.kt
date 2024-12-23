package com.eridiumcorp.bagz.components.accounts.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import com.eridiumcorp.bagz.app.models.Account

@Composable
fun AccountList(accounts: List<Account>, onAccountClick: (Account) -> Unit) {
    LazyColumn {
        items(accounts) { account ->
            AccountListItem(account, onAccountClick)
        }
    }
}