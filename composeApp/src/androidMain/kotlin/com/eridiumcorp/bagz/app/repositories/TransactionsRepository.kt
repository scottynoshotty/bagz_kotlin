package com.eridiumcorp.bagz.app.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eridiumcorp.bagz.app.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransactionsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    suspend fun getTransactions(accountId: String, startAfter: String? = null): List<Transaction> {
        var query = firestore.collection("transactions")
            .document(auth.uid!!)
            .collection("user_transactions")
            .whereEqualTo("account_id", accountId)
            .limit(30)

        if (startAfter != null) {
            val startAtSnapshot =
                firestore.collection("transactions")
                    .document(auth.uid!!)
                    .collection("user_transactions")
                    .document(startAfter)
                    .get()
                    .await()
            query = query.startAfter(startAtSnapshot)
        }

        val snapshot = query.get().await()
        return snapshot.documents.map { it.toTransaction() }
    }

    fun DocumentSnapshot.toTransaction() = Transaction.fromMap(this.data as Map<String, Any?>)
}

class TransactionsPagingSource(
    private val transactionsRepository: TransactionsRepository,
    private val accountId: String,
) : PagingSource<String, Transaction>() {

    override fun getRefreshKey(state: PagingState<String, Transaction>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.transactionId
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Transaction> {
        return try {
            val startAfter = params.key
            val transactions = transactionsRepository.getTransactions(accountId, startAfter)

            val nextKey = if (transactions.isEmpty()) {
                null
            } else {
                transactions.last().transactionId
            }

            LoadResult.Page(
                data = transactions,
                prevKey = params.key,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}