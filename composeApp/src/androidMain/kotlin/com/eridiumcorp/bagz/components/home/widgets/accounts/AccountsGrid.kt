package com.eridiumcorp.bagz.components.home.widgets.accounts

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Account
import com.eridiumcorp.bagz.app.utils.formatDouble
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.accounts.details.AccountDetails

@Composable
fun AccountsGrid(accounts: List<Account>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.height(400.dp)
    ) {
        items(accounts) { account ->
            AccountGridItem(account)
        }
    }
}

@Composable
fun AccountGridItem(account: Account, modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    Card(
        modifier = modifier.clickable {
            navController.navigate(AccountDetails(accountId = account.accountId))
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Display the logo or a default icon
                if (account.institution.logo != null) {
                    val decodedBytes = Base64.decode(account.institution.logo, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Institution Logo",
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                } else {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = "Account",
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                // Display the institution name
                Text(account.institution.name, color = MaterialTheme.colorScheme.onSurface)
            }
            // Display the account name on the next line
            Text(account.name, color = MaterialTheme.colorScheme.onSurface)
            if (account.mask != null) {
                Text("****${account.mask}", color = MaterialTheme.colorScheme.onSurface)
            }

            // Display the current balance
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Arrange items with space between
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (account.balances.current != null) {
                    Text(
                        "Balance: ${formatDouble(account.balances.current)}",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Forward arrow at the end of the row
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}