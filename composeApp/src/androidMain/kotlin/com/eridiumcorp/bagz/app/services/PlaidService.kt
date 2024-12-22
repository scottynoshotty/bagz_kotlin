package com.eridiumcorp.bagz.app.services

import com.google.firebase.Firebase
import com.google.firebase.functions.functions
import kotlinx.coroutines.tasks.await

class PlaidService {
    private val functions = Firebase.functions

    suspend fun createPlaidLinkToken(): String? {
        val data = mapOf(
            "products" to listOf("assets", "transactions")
        )

        val result = functions.getHttpsCallable("create_plaid_link_token").call(data).await()

        val anyResult: Any? = result.getData()
        if (anyResult is Map<*, *> && anyResult.containsKey("link_token") && anyResult["link_token"] is String) {
            return anyResult["link_token"] as String
        }
        return null
    }

    suspend fun linkInstitution(publicToken: String) {
        functions.getHttpsCallable("link_accounts").call(
            mapOf(
                "public_token" to publicToken
            )
        ).await()
    }
}