package com.eridiumcorp.bagz.components.bagz.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.app_name
import com.eridiumcorp.bagz.app.services.AuthService
import org.jetbrains.compose.resources.stringResource


@Composable
fun SignInScreen(modifier: Modifier = Modifier) =
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(Res.string.app_name))
            Text(text = AuthService.userId() ?: "No current user")
            GoogleSignInButton(onClick = { AuthService.signInWithGoogle() })
        }
    }