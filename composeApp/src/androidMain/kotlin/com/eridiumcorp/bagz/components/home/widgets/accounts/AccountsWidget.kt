package com.eridiumcorp.bagz.components.home.widgets.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.stringResource
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.accounts_widget_title
import bagz.composeapp.generated.resources.view_account_list_button_label
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.accounts.list.AccountListRoute

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AccountsWidget(
    viewModel: AccountsWidgetViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        when {
            uiState.loading -> CircularProgressIndicator()
            uiState.accounts.isEmpty() -> Text("You have not linked any accounts")
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.accounts_widget_title),
                            style = MaterialTheme.typography.headlineLargeEmphasized
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate(AccountListRoute)
                            }
                        ) {
                            Text(
                                text = stringResource(Res.string.view_account_list_button_label),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    AccountsGrid(accounts = uiState.accounts)
                }
            }
        }
    }
}