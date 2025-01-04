package com.eridiumcorp.bagz.components.link

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eridiumcorp.bagz.components.LocalNavController
import com.plaid.link.FastOpenPlaidLink
import com.plaid.link.Plaid
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun LinkHostScreen(modifier: Modifier) {
    val linkHostViewModel = koinViewModel<LinkHostViewModel>()
    val uiState = linkHostViewModel.uiState.collectAsState()
    val context = LocalContext.current as Activity
    var launched by remember { mutableStateOf(false) }
    val navController = LocalNavController.current

    val linkLauncher = rememberLauncherForActivityResult(
        contract = FastOpenPlaidLink()
    ) {
        when (it) {
            is LinkSuccess -> {
                linkHostViewModel.linkInstitution(it.publicToken)
                navController.popBackStack()
            }

            is LinkExit -> {
                navController.popBackStack()
            }
        }
    }

    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                uiState.value.loading -> CircularProgressIndicator()
                uiState.value.linkTokenConfiguration == null -> Text("Could not load link token")
                else -> {
                    val linkTokenConfiguration = uiState.value.linkTokenConfiguration!!

                    // Create a PlaidHandler
                    val plaidHandler = Plaid.create(context.application, linkTokenConfiguration)

                    if (!launched) {
                        launched = true
                        linkLauncher.launch(plaidHandler)
                    }
                }
            }
        }
    }
}