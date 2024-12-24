package com.eridiumcorp.bagz.components.signin

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import androidx.credentials.Credential

class SignInViewModel(val authService: AuthService) :
    AppViewModel() {
        
    fun signInWithGoogle(credential: Credential) {
        launchCatching {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            authService.signInWithGoogle(googleIdTokenCredential.idToken)
        }
    }
}