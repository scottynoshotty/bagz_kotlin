package com.eridiumcorp.bagz.components.accounts.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.components.accounts.list.AccountListItem
import com.eridiumcorp.bagz.components.transactions.list.TransactionsList
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailsScreen(modifier: Modifier) {
    val viewModel = koinViewModel<AccountDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.value.loading) {
                CircularProgressIndicator()
            } else if (uiState.value.account == null) {
                Text("Account not found")
            } else {
                AccountListItem(account = uiState.value.account!!) {}
                TransactionsList(pager = viewModel.transactions)
            }
        }
    }
}