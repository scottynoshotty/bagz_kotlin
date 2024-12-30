package com.eridiumcorp.bagz.components.home.widgets.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Account

@Composable
fun AccountsGrid(accounts: List<Account>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3 columns
        modifier = modifier
    ) {
        items(accounts) { account ->
            AccountGridItem(account) // Display each account in a card
        }
    }
}

@Composable
fun AccountGridItem(account: Account, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Add content to display account summary information
        Column(modifier = Modifier.padding(8.dp)) {
            Text(account.name)
            // Add more fields as needed (balance, mask, etc.)
        }
    }
}