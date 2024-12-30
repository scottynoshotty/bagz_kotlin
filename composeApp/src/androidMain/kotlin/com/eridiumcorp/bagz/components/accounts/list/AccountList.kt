package com.eridiumcorp.bagz.components.accounts.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.accounts.details.AccountDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountList(
    accountListViewModel: AccountListViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    val lazyPagingItems = accountListViewModel.accounts.flow.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier) {
        items(lazyPagingItems.itemCount, key = lazyPagingItems.itemKey { it.accountId }) { index ->
            val account = lazyPagingItems[index]
            if (account != null) {
                AccountListItem(account) { account ->
                    navController.navigate(AccountDetails(accountId = account.accountId))
                }
            }
        }
    }
}