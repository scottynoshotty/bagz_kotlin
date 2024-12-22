package com.eridiumcorp.bagz.app.repositories

import com.eridiumcorp.bagz.app.models.Account
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountsRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth

    fun getLinkedAccounts(): Flow<List<Account>> {
        return firestore.collection("accounts")
            .document(auth.uid!!)
            .collection("user_accounts")
            .limit(30)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { doc -> doc.toAccount() }
            }
    }
}

fun DocumentSnapshot.toAccount() = Account.fromMap(this.data as Map<String, Any?>)