package com.eridiumcorp.bagz.app.repositories

import com.eridiumcorp.bagz.app.models.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReportsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    fun getReport(): Flow<Report> {
        return firestore.collection("reports")
            .document(auth.uid!!)
            .snapshots()
            .map { snapshot ->
                snapshot.toReport()
            }

    }
}

fun DocumentSnapshot.toReport() = Report.fromMap(this.data as Map<String, Any?>)