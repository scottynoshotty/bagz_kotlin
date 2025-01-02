package com.eridiumcorp.bagz.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eridiumcorp.bagz.components.accounts.details.AccountDetails
import com.eridiumcorp.bagz.components.accounts.details.AccountDetailsScreen
import com.eridiumcorp.bagz.components.accounts.list.AccountListRoute
import com.eridiumcorp.bagz.components.accounts.list.AccountListScreen
import com.eridiumcorp.bagz.components.activity.ActivityRoute
import com.eridiumcorp.bagz.components.activity.ActivityScreen
import com.eridiumcorp.bagz.components.landing.Landing
import com.eridiumcorp.bagz.components.landing.LandingScreen
import com.eridiumcorp.bagz.components.link.LinkHost
import com.eridiumcorp.bagz.components.link.LinkHostScreen
import com.eridiumcorp.bagz.components.transactions.list.screens.detailed.DetailedTypeTransactions
import com.eridiumcorp.bagz.components.transactions.list.screens.detailed.DetailedTypeTransactionsListScreen
import com.eridiumcorp.bagz.components.transactions.list.screens.primary.PrimaryTypeTransactions
import com.eridiumcorp.bagz.components.transactions.list.screens.primary.PrimaryTypeTransactionsListScreen

@Composable
actual fun Bagz(modifier: Modifier) {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Landing) {
            composable<Landing> { LandingScreen(modifier) }
            composable<LinkHost> { LinkHostScreen(modifier) }
            composable<AccountDetails> { AccountDetailsScreen(modifier) }
            composable<AccountListRoute> { AccountListScreen(modifier) }
            composable<ActivityRoute> { ActivityScreen(modifier = modifier) }
            composable<PrimaryTypeTransactions> { PrimaryTypeTransactionsListScreen(modifier = modifier) }
            composable<DetailedTypeTransactions> { DetailedTypeTransactionsListScreen(modifier = modifier) }
        }
    }
}

val LocalNavController =
    compositionLocalOf<NavHostController> { error("No nav controller provided") }