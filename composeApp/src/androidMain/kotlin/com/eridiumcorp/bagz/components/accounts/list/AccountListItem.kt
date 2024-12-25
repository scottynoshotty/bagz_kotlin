package com.eridiumcorp.bagz.components.accounts.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Account

@Composable
fun AccountListItem(account: Account, onAccountClick: (Account) -> Unit) {
    val balance = when {
        account.balances.available != null -> account.balances.available.toString()
        account.balances.current != null -> account.balances.current.toString()
        else -> "N/A"
    }
    Card(
        elevation = 4.dp,
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
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Account Type: ${account.accountType}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Available Balance: $balance",
                style = MaterialTheme.typography.body1.copy(color = Color.Green)
            )
            account.mask?.let {
                Text(
                    text = "Masked Account Number: $it",
                    style = MaterialTheme.typography.body1.copy(color = Color.Gray)
                )
            }
        }
    }
}