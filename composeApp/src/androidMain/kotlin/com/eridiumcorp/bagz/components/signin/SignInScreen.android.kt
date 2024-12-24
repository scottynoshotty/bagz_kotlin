package com.eridiumcorp.bagz.components.signin

import android.content.Context
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.app_name
import bagz.composeapp.generated.resources.google
import bagz.composeapp.generated.resources.money_bag_outline_white
import bagz.composeapp.generated.resources.google_sign_in_button_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun SignInScreen(modifier: Modifier) {
    val context = LocalContext.current
    val viewModel = koinViewModel<SignInViewModel>()

    LaunchedEffect(Unit) {
        launchCredManBottomSheet(
            context,
            "120657644187-dnlsalb11o35680ifkigmd5un6249t6s.apps.googleusercontent.com"
        ) { credential ->
            viewModel.signInWithGoogle(credential)
        }
    }

    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
            )

            Row {
                Text(
                    style = MaterialTheme.typography.displayLargeEmphasized,
                    text = stringResource(Res.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Image(
                    painter = painterResource(Res.drawable.money_bag_outline_white),
                    contentDescription = "Money Bag",
                    modifier = Modifier.size(48.dp)
                )
            }
            GoogleSignInButton(
                { credential -> viewModel.signInWithGoogle(credential) },
                "120657644187-dnlsalb11o35680ifkigmd5un6249t6s.apps.googleusercontent.com"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GoogleSignInButton(
    onRequestResult: (Credential) -> Unit,
    oauthClientId: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 0.dp, bottom = 112.dp, end = 24.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        onClick = {
            coroutineScope.launch {
                try {
                    val signInWithGoogleOption = GetSignInWithGoogleOption
                        .Builder(serverClientId = oauthClientId)
                        .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(signInWithGoogleOption)
                        .build()

                    val result = CredentialManager.create(context).getCredential(
                        request = request,
                        context = context
                    )
                    onRequestResult(result.credential)
                } catch (_: NoCredentialException) {

                } catch (_: GetCredentialException) {

                }
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(Res.drawable.google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(Res.string.google_sign_in_button_label),
                style = MaterialTheme.typography.titleMediumEmphasized,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

suspend fun launchCredManBottomSheet(
    context: Context,
    oauthClientId: String,
    onRequestResult: (Credential) -> Unit,
) {
    try {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(oauthClientId)
            .build()

        val request = GetCredentialRequest(listOf(googleIdOption))

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )
        onRequestResult(result.credential)
    } catch (_: NoCredentialException) {

    } catch (_: GetCredentialException) {

    }
}