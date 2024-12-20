package com.eridiumcorp.bagz.components.signin

import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.sign_out_button_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignOutButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(modifier = modifier, onClick = onClick) {
        Text(text = stringResource(Res.string.sign_out_button_label))
    }
}