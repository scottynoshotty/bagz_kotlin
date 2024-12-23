package com.eridiumcorp.bagz.components.transactions.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import com.eridiumcorp.bagz.app.models.Transaction
import androidx.paging.compose.itemKey


@Composable
fun TransactionsList(pager: Pager<String, Transaction>) {
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
    LazyColumn {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.transactionId }
        ) { index ->
            val transaction = lazyPagingItems[index]
            if (transaction != null) {
                TransactionsListItem(transaction)
            }
        }
    }
}