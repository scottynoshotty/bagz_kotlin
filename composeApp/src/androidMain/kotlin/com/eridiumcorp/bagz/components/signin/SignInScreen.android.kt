package com.eridiumcorp.bagz.components.signin


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.app_name
import com.eridiumcorp.bagz.app.services.AuthService
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel


@Composable
actual fun SignInScreen(modifier: Modifier) {
    val viewModel = koinViewModel<SignInViewModel>()
    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(Res.string.app_name))
            Text(text = AuthService.currentUserId() ?: "No current user")
            GoogleSignInButton { credential ->
                {
                    viewModel.onSignInWithGoogle(credential = credential)
                }
            }
        }
    }
}