package com.eridiumcorp.bagz.components.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.money_bag_outline_white
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.home.widgets.accounts.AccountsWidget
import com.eridiumcorp.bagz.components.home.widgets.activity.ActivityWidget
import com.eridiumcorp.bagz.components.home.widgets.bag.BagWidget
import com.eridiumcorp.bagz.components.link.LinkHost
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import com.eridiumcorp.bagz.components.jarvis.bottomsheet.JarvisBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                expandedHeight = 42.dp,
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.money_bag_outline_white),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(38.dp)
                                .padding(4.dp)
                        )
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Insights",
                            modifier = Modifier
                                .size(38.dp)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .clickable {
                                    scope.launch { // Show bottom sheet when icon is clicked
                                        modalBottomSheetState.show()
                                    }
                                },
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 28.dp)
            ) {
                FloatingActionButton(onClick = { navController.navigate(LinkHost) }) {
                    Icon(Icons.Default.AddCircle, "Link Account")
                }

                FloatingActionButton(onClick = { viewModel.signOut() }) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, "Sign out")
                }
            }
        },
    ) { padding ->
        // Show JarvisBottomSheet when the sheet is open
        if (modalBottomSheetState.isVisible) {
            JarvisBottomSheet(onDismissRequest = { scope.launch { modalBottomSheetState.hide() } })
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.loading) {
                CircularProgressIndicator()
            } else {
                BagWidget()
                Spacer(modifier = Modifier.height(16.dp))
                AccountsWidget()
                Spacer(modifier = Modifier.height(16.dp))
                ActivityWidget()
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}