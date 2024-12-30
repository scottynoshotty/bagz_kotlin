package com.eridiumcorp.bagz.components.accounts.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.eridiumcorp.bagz.app.repositories.AccountsPagingSource
import com.eridiumcorp.bagz.app.repositories.AccountsRepository

class AccountListViewModel(private val accountsRepository: AccountsRepository) : ViewModel() {

    val accounts = Pager(PagingConfig(pageSize = 30)) {
        AccountsPagingSource(accountsRepository)
    }
}