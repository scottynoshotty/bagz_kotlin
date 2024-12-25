package com.eridiumcorp.bagz.app.services

import cocoapods.FirebaseAuth.*
import com.eridiumcorp.bagz.app.models.User
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class AuthService {
    private val auth = FIRAuth.auth()

    actual fun currentUser(): Flow<User?> = flowOf(null)

    actual fun currentUserId(): String? = auth.currentUser()?.uid

    actual suspend fun signInWithGoogle(token: String) {}

    actual suspend fun signOut() {}

    actual suspend fun deleteAccount() {}


}