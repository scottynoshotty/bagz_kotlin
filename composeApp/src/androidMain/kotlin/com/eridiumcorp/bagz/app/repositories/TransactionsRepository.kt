package com.eridiumcorp.bagz.app.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TransactionsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {
}