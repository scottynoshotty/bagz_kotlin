package com.eridiumcorp.bagz.components.home.widgets.activity

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.activity_widget_title
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ActivityWidget(
    viewModel: ActivityWidgetViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        when {
            uiState.loading -> CircularProgressIndicator()
            uiState.activity == null -> Text("No activity available")
            uiState.activity!!.activityData == null -> Text("No activity available")
            uiState.activity!!.activityData!!.isEmpty() -> Text("No activity available")
            else -> {
                val activity = uiState.activity!!
                val activityData = activity.activityData!!
                var data = activityData.map { activitySummary ->
                    Pie(
                        data = activitySummary.amount,
                        label = activitySummary.type.name,
                        color = activitySummary.type.color
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(Res.string.activity_widget_title),
                        style = MaterialTheme.typography.headlineLargeEmphasized
                    )
                    PieChart(
                        modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally),
                        data = data,
                        style = Pie.Style.Fill,
                        onPieClick = {
                            println("123456789 ${it.label} Clicked")
                            val pieIndex = data.indexOf(it)
                            data =
                                data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
                        },
                        selectedScale = 1.2f,
                        scaleAnimEnterSpec = spring<Float>(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        colorAnimEnterSpec = tween(300),
                        colorAnimExitSpec = tween(300),
                        scaleAnimExitSpec = tween(300),
                        spaceDegreeAnimExitSpec = tween(300),
                    )
                }
            }
        }
    }
}