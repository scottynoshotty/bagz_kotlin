package com.eridiumcorp.bagz.components.signin

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.google_sign_in_button_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedButton(
        modifier = modifier, onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = stringResource(Res.string.google_sign_in_button_label))
    }
}