package com.eridiumcorp.bagz.components.signin

import android.content.Context
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewModelScope
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.components.app.AppViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SignInViewModel(val applicationContext: Context) : AppViewModel() {

    private val authService: AuthService by inject()

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authService.currentUser().collect { user ->
                _uiState.value = SignInUiState(user)
            }
        }
    }

    fun signInWithGoogle() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId("120657644187-dnlsalb11o35680ifkigmd5un6249t6s.apps.googleusercontent.com")
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        launchCatching {
            val result = CredentialManager.create(applicationContext).getCredential(
                request = request,
                context = applicationContext
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            authService.signInWithGoogle(googleIdTokenCredential.idToken)
        }
    }

    fun signOut() {
        launchCatching {
            authService.signOut()
        }
    }
}