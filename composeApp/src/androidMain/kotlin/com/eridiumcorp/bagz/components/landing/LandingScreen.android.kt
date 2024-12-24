package com.eridiumcorp.bagz.components.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.eridiumcorp.bagz.components.home.HomeScreen
import com.eridiumcorp.bagz.components.signin.SignInScreen
import org.koin.androidx.compose.koinViewModel

@Composable
actual fun LandingScreen(modifier: Modifier) {
    val landingViewModel: LandingViewModel = koinViewModel<LandingViewModel>()
    val uiState = landingViewModel.uiState.collectAsState()
    when {
        uiState.value.loading -> {
            Column(
                modifier = modifier
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
            ) {}
        }

        uiState.value.user == null -> SignInScreen(modifier)
        else -> HomeScreen(modifier)
    }
}