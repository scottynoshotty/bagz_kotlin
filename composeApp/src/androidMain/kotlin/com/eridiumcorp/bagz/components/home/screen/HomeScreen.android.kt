package com.eridiumcorp.bagz.components.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.home.widgets.accounts.AccountsWidget
import com.eridiumcorp.bagz.components.home.widgets.bag.BagWidget
import com.eridiumcorp.bagz.components.link.LinkHost
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.loading) {
                CircularProgressIndicator()
            } else {
                AccountsWidget()
                Spacer(modifier = Modifier.height(16.dp))
                BagWidget()
            }
        }
    }
}