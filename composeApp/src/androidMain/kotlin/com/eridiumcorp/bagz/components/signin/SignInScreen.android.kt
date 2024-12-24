package com.eridiumcorp.bagz.components.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.app_name
import bagz.composeapp.generated.resources.google
import bagz.composeapp.generated.resources.google_sign_in_button_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun SignInScreen(modifier: Modifier) {
    val signInViewModel: SignInViewModel = koinViewModel<SignInViewModel>()
    val uiState = signInViewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = modifier
                .padding()
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary, // Top color
                            MaterialTheme.colorScheme.secondary // Bottom color
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(Res.string.app_name),
                color = Color.White // Example: Set text color for contrast
            )
            GoogleSignInButton(onClick = { signInViewModel.signInWithGoogle() })
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White // Or your desired background color
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.google_sign_in_button_label),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black // Or your desired text color
            )
        }
    }
}