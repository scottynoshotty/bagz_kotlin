package com.eridiumcorp.bagz.app.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eridiumcorp.bagz.app.models.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class AccountsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    fun getLinkedAccounts(): Flow<List<Account>> {
        return firestore.collection("accounts")
            .document(auth.uid!!)
            .collection("user_accounts")
            .limit(6)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { doc -> doc.toAccount() }
            }
    }

    suspend fun getAccounts(startAfter: String? = null): List<Account> {
        var query = firestore.collection("accounts")
            .document(auth.uid!!)
            .collection("user_accounts")
            .limit(30)

        if (startAfter != null) {
            val startAtSnapshot =
                firestore.collection("accounts")
                    .document(auth.uid!!)
                    .collection("user_accounts")
                    .document(startAfter)
                    .get()
                    .await()
            query = query.startAfter(startAtSnapshot)
        }
        val snapshot = query.get().await()
        return snapshot.documents.map { it.toAccount() }
    }

    suspend fun getAccountById(accountId: String): Account? {
        val document = firestore.collection("accounts")
            .document(auth.uid!!)
            .collection("user_accounts")
            .document(accountId)
            .get().await()

        return if (document.exists()) document.toAccount() else null
    }

    fun DocumentSnapshot.toAccount() = Account.fromMap(this.data as Map<String, Any?>)
}

class AccountsPagingSource(private val accountsRepository: AccountsRepository) :
    PagingSource<String, Account>() {

    override fun getRefreshKey(state: PagingState<String, Account>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(
                anchorPosition
            )?.accountId
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Account> {
        return try {
            val startAfter = params.key
            val accounts = accountsRepository.getAccounts(startAfter)
            val nextKey = if (accounts.isEmpty()) {
                null
            } else {
                accounts.last().accountId
            }
            LoadResult.Page(
                data = accounts,
                prevKey = params.key,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}

