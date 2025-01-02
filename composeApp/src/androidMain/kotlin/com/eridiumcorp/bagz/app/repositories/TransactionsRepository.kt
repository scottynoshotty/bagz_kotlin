package com.eridiumcorp.bagz.app.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eridiumcorp.bagz.app.models.DetailedActivityType
import com.eridiumcorp.bagz.app.models.PrimaryActivityType
import com.eridiumcorp.bagz.app.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransactionsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    suspend fun getTransactions(
        accountId: String? = null,
        primaryType: PrimaryActivityType? = null,
        detailedType: DetailedActivityType? = null,
        startDate: String? = null,
        endDate: String? = null,
        startAfter: String? = null,
    ): List<Transaction> {
        var query = firestore.collection("transactions")
            .document(auth.uid!!)
            .collection("user_transactions")
            .limit(30)

        if (accountId != null) {
            query = query.whereEqualTo("account_id", accountId)
        }
        if (primaryType != null) {
            query = query.whereEqualTo(
                "transaction_data.personal_finance_category.primary",
                primaryType.name
            )
        }
        if (detailedType != null) {
            query = query.whereEqualTo(
                "transaction_data.personal_finance_category.detailed",
                detailedType.name
            )
        }
        if (startDate != null) {
            query = query.whereGreaterThanOrEqualTo("transaction_data.date", startDate)
        }
        if (endDate != null) {
            query = query.whereLessThanOrEqualTo("transaction_data.date", endDate)
        }
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
    private val accountId: String? = null,
    private val primaryType: PrimaryActivityType? = null,
    private val detailedType: DetailedActivityType? = null,
    private val startDate: String? = null,
    private val endDate: String? = null,
) : PagingSource<String, Transaction>() {

    override fun getRefreshKey(state: PagingState<String, Transaction>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.transactionId
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Transaction> {
        return try {
            val startAfter = params.key
            val transactions = transactionsRepository.getTransactions(
                accountId = accountId,
                primaryType = primaryType,
                detailedType = detailedType,
                startDate = startDate,
                endDate = endDate,
                startAfter = startAfter
            )

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