package com.eridiumcorp.bagz.components.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eridiumcorp.bagz.app.models.Report
import com.eridiumcorp.bagz.components.app.AppLineChart

@Composable
fun ReportSummary(report: Report, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(scrollState)
    ) {
        ReportCard(
            report = report,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ReportCard(report: Report, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        TimeSeriesGraph(report)
    }
}

@Composable
fun TimeSeriesGraph(report: Report, modifier: Modifier = Modifier) {
    AppLineChart(report, modifier)
}

