package com.eridiumcorp.bagz.app.repositories

import com.eridiumcorp.bagz.app.models.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReportsRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    fun getReport(): Flow<Report> = flow {
        delay(2000)
        emit(
            Report(
                date = "12/25/24",
                net = 724916.23,
                netDiff = 1321.43,
                cash = 8452.21,
                cashDiff = 21.22,
                investments = 843012.45,
                investmentsDiff = 3728.21,
                debt = 126548.43,
                debtDiff = 2428.00
            )
        )
    }
}