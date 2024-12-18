package com.eridiumcorp.bagz.app.services

import com.eridiumcorp.bagz.app.models.User
import kotlinx.coroutines.flow.Flow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AuthService {
    companion object {
        fun currentUser(): Flow<User?>
        fun currentUserId(): String?
        fun signInWithGoogle()
        suspend fun signOut()
        suspend fun deleteAccount()
    }
}