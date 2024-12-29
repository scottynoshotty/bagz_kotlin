package com.eridiumcorp.bagz.components.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.eridiumcorp.bagz.app.utils.formatDouble
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun AppLineChart(values: List<Double>, labels: List<String>, modifier: Modifier = Modifier) {
    val chartLineColor = MaterialTheme.colorScheme.primary
    val data =
        listOf(
            Line(
                label = "",
                values = values,
                color = SolidColor(chartLineColor)
            ),
        )


    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(250.dp),
        data = data,
        labelProperties = LabelProperties(
            enabled = true,
            labels = labels
        ),
        labelHelperProperties = LabelHelperProperties(enabled = false),
        gridProperties = GridProperties(enabled = false),
        indicatorProperties = HorizontalIndicatorProperties(
            contentBuilder = { indicator ->
                formatDouble(indicator)
            },
        )
    )

}