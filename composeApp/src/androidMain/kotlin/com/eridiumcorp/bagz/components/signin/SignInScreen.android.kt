package com.eridiumcorp.bagz.components.signin


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel


@Composable
actual fun SignInScreen(modifier: Modifier) {
    val signInViewModel: SignInViewModel = koinViewModel<SignInViewModel>()
    val uiState = signInViewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(Res.string.app_name))
            if (uiState.value.user == null) {
                Text(text = "No current user")
                GoogleSignInButton {
                    signInViewModel.signInWithGoogle()
                }
            } else {
                Text(text = "Greetings ${uiState.value.user!!.displayName}")
                SignOutButton { signInViewModel.signOut() }
            }
        }
    }
}