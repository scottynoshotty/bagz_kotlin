package com.eridiumcorp.bagz.components.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivityScreen(
    activityScreenViewModel: ActivityScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by activityScreenViewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { padding ->
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