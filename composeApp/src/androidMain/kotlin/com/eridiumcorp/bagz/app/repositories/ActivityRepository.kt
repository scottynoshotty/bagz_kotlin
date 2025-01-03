package com.eridiumcorp.bagz.app.repositories

import com.eridiumcorp.bagz.app.models.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ActivityRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {
    fun getActivity(): Flow<Activity> {
        return firestore.collection("activity")
            .document(auth.uid!!)
            .snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toActivity()
                } else {
                    Activity.fromMap(emptyMap())
                }
            }
    }
}

fun DocumentSnapshot.toActivity() = Activity.fromMap(this.data as Map<String, Any?>)