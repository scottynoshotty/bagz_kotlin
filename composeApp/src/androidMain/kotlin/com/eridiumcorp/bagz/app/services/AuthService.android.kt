package com.eridiumcorp.bagz.app.services

import com.eridiumcorp.bagz.app.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AuthService {
    actual companion object {
        private val auth = Firebase.auth

        actual fun currentUser(): Flow<User?> = flowOf(null)

        actual fun currentUserId(): String? = auth.uid

        actual fun signInWithGoogle() {
        }

        actual suspend fun signOut() {
            auth.signOut()
        }

        actual suspend fun deleteAccount() {}

    }
}