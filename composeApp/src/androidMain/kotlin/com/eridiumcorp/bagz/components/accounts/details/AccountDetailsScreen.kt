package com.eridiumcorp.bagz.components.accounts.details

import androidx.compose.foundation.layout.Box
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
fun AccountDetailsScreen(modifier: Modifier) {
    val viewModel = koinViewModel<AccountDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.value.loading) {
                CircularProgressIndicator()
            } else {
                Text("Welcome to the account details page")
            }
        }


    }
}