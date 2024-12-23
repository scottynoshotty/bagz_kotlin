package com.eridiumcorp.bagz.components.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.components.home.HomeScreen
import com.eridiumcorp.bagz.components.signin.SignInScreen
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun LandingScreen(modifier: Modifier) {
    val landingViewModel: LandingViewModel = koinViewModel<LandingViewModel>()
    val uiState = landingViewModel.uiState.collectAsState()
    when {
        uiState.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.value.user == null -> SignInScreen(modifier)
        else -> HomeScreen(modifier)
    }
}