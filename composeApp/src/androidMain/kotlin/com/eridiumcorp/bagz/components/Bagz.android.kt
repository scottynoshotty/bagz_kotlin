package com.eridiumcorp.bagz.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eridiumcorp.bagz.components.landing.Landing
import com.eridiumcorp.bagz.components.landing.LandingScreen
import com.eridiumcorp.bagz.components.link.LinkHost
import com.eridiumcorp.bagz.components.link.LinkHostScreen

@Composable
actual fun Bagz(modifier: Modifier) {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Landing) {
            composable<Landing> { LandingScreen(modifier) }
            composable<LinkHost> { LinkHostScreen(modifier) }
        }
    }
}

val LocalNavController =
    compositionLocalOf<NavHostController> { error("No nav controller provided") }