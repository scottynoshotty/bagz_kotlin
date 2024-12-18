package com.eridiumcorp.bagz.app.services

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AuthService {
    companion object {
        fun userId(): String?
        fun signInWithGoogle()
    }
}