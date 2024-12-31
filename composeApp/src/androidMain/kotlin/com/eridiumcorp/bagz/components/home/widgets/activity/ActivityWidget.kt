package com.eridiumcorp.bagz.components.home.widgets.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.activity_widget_title
import bagz.composeapp.generated.resources.view_activity_button_label
import com.eridiumcorp.bagz.app.utils.getCurrentMonthName
import com.eridiumcorp.bagz.components.LocalNavController
import com.eridiumcorp.bagz.components.activity.ActivityRoute
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
    val navController = LocalNavController.current
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = stringResource(Res.string.activity_widget_title),
                                style = MaterialTheme.typography.headlineLargeEmphasized
                            )
                            Text(
                                text = getCurrentMonthName(),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate(ActivityRoute)
                            }
                        ) {
                            Text(
                                text = stringResource(Res.string.view_activity_button_label),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
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