package com.eridiumcorp.bagz.components.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.models.Holdings
import com.eridiumcorp.bagz.app.models.Report
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun AppLineChart(report: Report, modifier: Modifier) {
    val weeklyHoldings: List<Holdings?> = report.getLast7DaysHoldings()
    val netWorthList = weeklyHoldings.mapNotNull { it?.holdingsData?.net }
    val chartLineColor = MaterialTheme.colorScheme.primary
    val data = remember {
        listOf(
            Line(
                label = "",
                values = netWorthList,
                color = SolidColor(chartLineColor)
            ),
        )
    }

    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        data = data,
        labelHelperProperties = LabelHelperProperties(
            enabled = false,
        ),
        gridProperties = GridProperties(enabled = false)
    )

}