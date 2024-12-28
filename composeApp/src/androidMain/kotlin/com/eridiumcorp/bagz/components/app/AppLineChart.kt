package com.eridiumcorp.bagz.components.app

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eridiumcorp.bagz.app.models.Holdings
import com.eridiumcorp.bagz.app.models.Report
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.extensions.format
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties


@Composable
fun AppLineChart(report: Report, modifier: Modifier) {
    val weeklyHoldings: List<Holdings?> = report.getLast7DaysHoldings()
    val netWorthList = weeklyHoldings.mapNotNull { it?.holdingsData?.net }
    val chartColor = MaterialTheme.colorScheme.primary
    val data = remember {
        listOf(
            Line(
                label = "",
                values = netWorthList,
                color = SolidColor(chartColor)
            ),
        )
    }
    Card(
        modifier = modifier
            .height(270.dp)
            .fillMaxWidth()
            .border(2.dp, Color.Transparent, RoundedCornerShape(12.dp)),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff2D2D2D)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 2.dp),
                data = data,
                animationMode = AnimationMode.Together(delayBuilder = {
                    it * 500L
                }),
                popupProperties = PopupProperties(
                    textStyle = TextStyle(
                        fontSize = 11.sp,
                        color = Color.White,
                    ),
                    contentBuilder = {
                        it.format(1) + " Million"
                    },
                    containerColor = Color(0xff414141)
                ),
                indicatorProperties = HorizontalIndicatorProperties(
                    textStyle = TextStyle(
                        fontSize = 11.sp,
                        color = Color.White
                    ),
                    contentBuilder = {
                        it.format(1) + " M"
                    }
                ),
                labelHelperProperties = LabelHelperProperties(
                    enabled = false,
                ),
                curvedEdges = true,
                labelProperties = LabelProperties(
                    enabled = true,
                    labels = listOf("Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"),
                    textStyle = TextStyle(
                        fontSize = 11.sp,
                        color = Color.White
                    ),
                ),
            )
        }
    }
}