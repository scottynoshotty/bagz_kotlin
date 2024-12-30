package com.eridiumcorp.bagz.components.accounts.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountListScreen(modifier: Modifier = Modifier) {
    Scaffold { padding ->
        AccountList(modifier = modifier.padding())
    }
}