package com.eridiumcorp.bagz.components.home.widgets.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.activity_widget_title
import com.eridiumcorp.bagz.app.utils.getCurrentMonthName
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
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
                var data = activityData.take(6).map { activitySummary ->
                    Bars(
                        label = activitySummary.type.name.substring(0, 3),
                        values = listOf(
                            Bars.Data(
                                value = activitySummary.amount,
                                color = SolidColor(activitySummary.type.color)
                            )
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.activity_widget_title),
                        style = MaterialTheme.typography.headlineLargeEmphasized
                    )
                    Text(
                        text = getCurrentMonthName(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(Modifier.height(8.dp))
                    ColumnChart(
                        modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth(),
                        data = data,
                        labelHelperProperties = LabelHelperProperties(enabled = false),
                        gridProperties = GridProperties(enabled = false)
                    )
                }
            }
        }
    }
}