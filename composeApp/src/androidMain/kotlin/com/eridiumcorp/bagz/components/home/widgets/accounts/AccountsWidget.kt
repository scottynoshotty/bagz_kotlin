package com.eridiumcorp.bagz.components.home.widgets.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.stringResource
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.accounts_widget_title

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AccountsWidget(
    viewModel: AccountsWidgetViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        when {
            uiState.loading -> CircularProgressIndicator()
            uiState.accounts.isEmpty() -> Text("You have not linked any accounts")
            else -> {
                Column {
                    Text(
                        text = stringResource(Res.string.accounts_widget_title),
                        style = MaterialTheme.typography.headlineLargeEmphasized
                    )
                    AccountsGrid(accounts = uiState.accounts)
                }
            }
        }
    }
}