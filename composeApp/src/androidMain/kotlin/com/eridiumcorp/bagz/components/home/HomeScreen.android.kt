package com.eridiumcorp.bagz.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.accounts.details.AccountDetails
import com.eridiumcorp.bagz.components.accounts.list.AccountList
import com.eridiumcorp.bagz.components.link.LinkHost
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun HomeScreen(modifier: Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val navController = LocalNavController.current
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 28.dp)
            ) {
                FloatingActionButton(onClick = { navController.navigate(LinkHost) }) {
                    Icon(Icons.Default.AddCircle, "Link Account")
                }

                FloatingActionButton(onClick = { viewModel.signOut() }) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, "Sign out")
                }
            }
        },
        modifier = modifier,
    ) { padding ->
        if (uiState.value.accounts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("There are no accounts.")
            }
        } else {
            AccountList(accounts = uiState.value.accounts, onAccountClick = {
                navController.navigate(AccountDetails(it.accountId))
            })
        }

    }
}