package com.eridiumcorp.bagz.components.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                expandedHeight = 42.dp,
                title = {
                    Text("Your activity")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = modifier) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (uiState.activity?.activityData != null) {
                val activityData = uiState.activity!!.activityData!!
                for (activitySummary in activityData) {
                    Text("${activitySummary.primaryActivitySummary.type.name}: ${activitySummary.primaryActivitySummary.amount}")
                    for (detailedActivitySummary in activitySummary.detailedActivitySummaries) {
                        Text("${detailedActivitySummary.type.name}: ${detailedActivitySummary.amount}")
                    }
                }
            } else {
                Text("No activity data")
            }
        }
    }
}