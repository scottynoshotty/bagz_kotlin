package com.eridiumcorp.bagz.components.accounts.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.components.accounts.list.AccountListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailsScreen(modifier: Modifier) {
    val viewModel = koinViewModel<AccountDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when {
                uiState.value.loading -> CircularProgressIndicator()
                uiState.value.account == null -> Text("Account not found")
                else -> AccountListItem(account = uiState.value.account!!) { }
            }
        }
    }
}