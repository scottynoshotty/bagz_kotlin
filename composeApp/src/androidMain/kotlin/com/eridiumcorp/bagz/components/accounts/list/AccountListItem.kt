package com.eridiumcorp.bagz.components.accounts.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Account
import com.eridiumcorp.bagz.app.utils.formatDouble

@Composable
fun AccountListItem(account: Account, onAccountClick: (Account) -> Unit) {
    val balance = when {
        account.balances.available != null -> formatDouble(account.balances.available).toString()
        account.balances.current != null -> formatDouble(account.balances.current).toString()
        else -> "N/A"
    }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onAccountClick(account) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = account.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Account Type: ${account.accountType}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Available Balance: $balance",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Green)
            )
            account.mask?.let {
                Text(
                    text = "Masked Account Number: $it",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
            }
        }
    }
}