package com.eridiumcorp.bagz.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                .padding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.loading) {
                CircularProgressIndicator()
            } else if (uiState.report != null) {

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    SegmentedButtonSelector(
                        selected = uiState.reportTimePeriod,
                        onSelected = { viewModel.setReportTimePeriod(it) },
                        options = ReportTimePeriod.values(),
                    )

                    SegmentedButtonSelector(
                        selected = uiState.reportHoldingsType,
                        onSelected = { viewModel.setReportHoldingsType(it) },
                        options = ReportHoldingsType.values(),
                    )
                }

                // Holdings chart
                AppLineChart(values = uiState.graphValues, labels = uiState.graphLabels)
            } else {
                Text("No report available")
            }
        }
    }
}

@Composable
fun <T : Enum<T>> SegmentedButtonSelector(
    selected: T,
    onSelected: (T) -> Unit,
    options: Array<T>,
    modifier: Modifier = Modifier,
) {
    MultiChoiceSegmentedButtonRow(modifier = modifier.size(200.dp)) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                contentPadding = PaddingValues(2.dp),
                checked = selected == option,
                onCheckedChange = { onSelected(option) },
                label = {
                    val cappedName = if (option.name.length > 6) {
                        option.name.substring(0, 6)
                    } else {
                        option.name
                    }
                    Text(cappedName, fontSize = 8.sp)
                })
        }
    }
}