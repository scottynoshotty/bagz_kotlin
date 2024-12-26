package com.eridiumcorp.bagz.components.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Report

@Composable
fun ReportSummary(report: Report, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Report (${report.date})")
            Spacer(modifier = Modifier.height(16.dp))
            ReportItem(label = "Net", value = report.net, diff = report.netDiff)
            ReportItem(label = "Cash", value = report.cash, diff = report.cashDiff)
            ReportItem(
                label = "Investments",
                value = report.investments,
                diff = report.investmentsDiff
            )
            ReportItem(label = "Debt", value = report.debt, diff = report.debtDiff)
        }
    }
}

@Composable
fun ReportItem(label: String, value: Double, diff: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$label: $$value")
        if (diff > 0) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Up Arrow",
                tint = Color.Green
            )
        } else if (diff < 0) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Down Arrow",
                tint = Color.Red
            )
        }
    }
}

