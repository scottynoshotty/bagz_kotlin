package com.eridiumcorp.bagz.components.landing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.components.home.HomeScreen
import com.eridiumcorp.bagz.components.signin.SignInScreen
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun LandingScreen(modifier: Modifier) {
    val landingViewModel: LandingViewModel = koinViewModel<LandingViewModel>()
    val uiState = landingViewModel.uiState.collectAsState()
    when {
        uiState.value.userId == null -> SignInScreen(modifier)
        else -> HomeScreen(modifier)
    }
}