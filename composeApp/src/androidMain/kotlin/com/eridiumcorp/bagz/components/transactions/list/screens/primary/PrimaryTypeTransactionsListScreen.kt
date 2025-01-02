package com.eridiumcorp.bagz.components.transactions.list.screens.primary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.components.transactions.list.TransactionsList
import org.koin.androidx.compose.koinViewModel

@Composable
fun PrimaryTypeTransactionsListScreen(
    viewModel: PrimaryTypeTransactionsViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(modifier) { padding ->
        TransactionsList(uiState.pager, modifier = Modifier.padding(padding))
    }
}