package com.eridiumcorp.bagz.components.signin

import android.content.Context
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.google_sign_in_button_label
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier, onRequestResult: (Credential) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val webClientId = stringResource(Res.string.google_sign_in_button_label)
    OutlinedButton(modifier = modifier, onClick =
    {
        coroutineScope.launch {
            launchCredManBottomSheet(context, webClientId, onRequestResult)
        }
    }) {
        Text(text = stringResource(Res.string.google_sign_in_button_label))
    }
}

suspend fun launchCredManBottomSheet(
    context: Context,
    webClientId: String,
    onRequestResult: (Credential) -> Unit,
) {
    try {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

        onRequestResult(result.credential)
    } catch (e: NoCredentialException) {
    } catch (e: GetCredentialException) {
    }
}