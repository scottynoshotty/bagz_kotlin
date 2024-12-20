package com.eridiumcorp.bagz.app.services

import com.eridiumcorp.bagz.app.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AuthService {
    private val auth = Firebase.auth

    actual fun currentUser(): Flow<User?> = callbackFlow {
        val listener =
            FirebaseAuth.AuthStateListener { auth ->
                if (auth.currentUser == null) {
                    this.trySend(null)
                } else {
                    val fireUser = auth.currentUser!!
                    this.trySend(
                        User(
                            fireUser.uid,
                            fireUser.email ?: "",
                            fireUser.providerId,
                            fireUser.displayName ?: "",
                            fireUser.isAnonymous
                        )
                    )
                }

            }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    actual fun currentUserId(): String? = auth.uid

    actual suspend fun signInWithGoogle(token: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        val authResult = auth.signInWithCredential(firebaseCredential).await()
    }

    actual suspend fun signOut() {
        auth.signOut()
    }

    actual suspend fun deleteAccount() {}


}