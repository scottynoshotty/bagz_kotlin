package com.eridiumcorp.bagz.components.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    activityScreenViewModel: ActivityScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by activityScreenViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your activity") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = modifier
    ) { padding ->
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(scrollState)) {
            if (uiState.activity?.activityData != null) {
                val activityData = uiState.activity!!.activityData!!
                for (activitySummary in activityData) {
                    ListItem(
                        headlineContent = {
                            Text(
                                "${activitySummary.primaryActivitySummary.type.name}: ${activitySummary.primaryActivitySummary.amount}",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    for (detailedActivitySummary in activitySummary.detailedActivitySummaries) {
                        ListItem(
                            headlineContent = {
                                Text(
                                    "${detailedActivitySummary.type.name}: ${detailedActivitySummary.amount}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        )
                    }
                }
            } else {
                Text("No activity data")
            }
        }
    }
}