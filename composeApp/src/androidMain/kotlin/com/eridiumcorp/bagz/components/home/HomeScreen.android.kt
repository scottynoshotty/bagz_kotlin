package com.eridiumcorp.bagz.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.app.AppLineChart
import com.eridiumcorp.bagz.components.link.LinkHost
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), modifier: Modifier) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.loading) {
                CircularProgressIndicator()
            } else if (uiState.report != null) {

                // Time period selector with onSelected callback
                ReportTimePeriodSelector(
                    selected = uiState.reportTimePeriod,
                    onSelected = { viewModel.setReportTimePeriod(it) }
                )

                // Holdings type selector with onSelected callback
                ReportHoldingsTypeSelector(
                    selected = uiState.reportHoldingsType,
                    onSelected = { viewModel.setReportHoldingsType(it) }
                )

                // Holdings chart
                AppLineChart(values = uiState.graphValues, labels = uiState.graphLabels)
            } else {
                Text("No report available")
            }
        }
    }
}

@Composable
fun ReportTimePeriodSelector(
    selected: ReportTimePeriod,
    onSelected: (ReportTimePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        ReportTimePeriod.entries.forEach { timePeriod ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selected == timePeriod,
                    onClick = { onSelected(timePeriod) }
                )
                Text(timePeriod.name)
            }
        }
    }
}

@Composable
fun ReportHoldingsTypeSelector(
    selected: ReportHoldingsType,
    onSelected: (ReportHoldingsType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        ReportHoldingsType.entries.forEach { holdingsType ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selected == holdingsType,
                    onClick = { onSelected(holdingsType) }
                )
                Text(holdingsType.name)
            }
        }
    }
}