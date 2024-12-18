package com.eridiumcorp.bagz.app.services

import cocoapods.FirebaseAuth.*
import kotlinx.cinterop.ExperimentalForeignApi

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class AuthService {
    actual companion object {
        private val auth = FIRAuth.auth()

        actual fun userId(): String? = auth.currentUser()?.uid()

        actual fun signInWithGoogle() {}

        actual fun signOut() {}
    }
}