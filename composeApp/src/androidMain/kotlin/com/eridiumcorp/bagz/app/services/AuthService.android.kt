package com.eridiumcorp.bagz.app.services

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AuthService {
    actual companion object {

        private val auth = Firebase.auth

        actual fun userId(): String? = auth.uid
        actual fun signInWithGoogle() {
        }

        actual fun signOut() {
            auth.signOut()
        }
    }
}