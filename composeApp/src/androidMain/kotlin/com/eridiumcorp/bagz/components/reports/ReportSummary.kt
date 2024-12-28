package com.eridiumcorp.bagz.components.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.eridiumcorp.bagz.app.models.Report
import com.eridiumcorp.bagz.components.app.AppLineChart

@Composable
fun ReportSummary(report: Report, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val holdings = report.holdingsMap.values.firstOrNull() // Get the first (and only) entry

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(scrollState)
    ) {
        ReportCard(
            title = "Net",
            value = holdings?.holdingsData?.net ?: 0.0,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(
            title = "Cash",
            value = holdings?.holdingsData?.cash ?: 0.0,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(
            title = "Investments",
            value = holdings?.holdingsData?.investments ?: 0.0,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(
            title = "Debt",
            value = holdings?.holdingsData?.debt ?: 0.0,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ReportCard(title: String, value: Double, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("$value", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Spacer(modifier = Modifier.height(16.dp))
            TimeSeriesGraph()
        }
    }
}

@Composable
fun TimeSeriesGraph(modifier: Modifier = Modifier) {
    AppLineChart(modifier)
}

