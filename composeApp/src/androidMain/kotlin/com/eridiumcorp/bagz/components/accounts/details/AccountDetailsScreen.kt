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
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailsScreen(accountDetails: AccountDetails, modifier: Modifier) {
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
            if (uiState.value.loading) {
                CircularProgressIndicator()
            } else {
                Text("Welcome to the account details page")
                Text("Account id: ${accountDetails.accountId}")
            }
        }
    }
}