package com.eridiumcorp.bagz.app.repositories

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

    suspend fun getAccountById(accountId: String): Account? {
        val document = firestore.collection("accounts")
            .document(auth.uid!!)
            .collection("user_accounts")
            .document(accountId)
            .get().await()

        return if (document.exists()) document.toAccount() else null
    }
}

fun DocumentSnapshot.toAccount() = Account.fromMap(this.data as Map<String, Any?>)