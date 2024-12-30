package com.eridiumcorp.bagz.components.home.widgets.bag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.sp
import bagz.composeapp.generated.resources.Res
import bagz.composeapp.generated.resources.bag_widget_title
import com.eridiumcorp.bagz.app.models.Report
import com.eridiumcorp.bagz.app.utils.formatDouble
import com.eridiumcorp.bagz.components.app.AppLineChart

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BagWidget(viewModel: BagWidgetViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        when {
            uiState.loading -> CircularProgressIndicator()
            uiState.report == null -> Text("No report available")
            else -> {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.bag_widget_title),
                            style = MaterialTheme.typography.headlineLargeEmphasized
                        )
                        BagSummary(uiState.report!!)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AppLineChart(values = uiState.graphValues, labels = uiState.graphLabels)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
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
                }
            }
        }
    }
}

@Composable
fun BagSummary(report: Report, modifier: Modifier = Modifier) {
    val currentHoldings = report.getCurrentDayHoldings()
    if (currentHoldings != null) {
        val net = currentHoldings.holdingsData.net
        val cash = currentHoldings.holdingsData.cash
        val invest = currentHoldings.holdingsData.investments
        val debt = currentHoldings.holdingsData.debt
        if (net != null && cash != null && invest != null && debt != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
                Text("Net: ${formatDouble(net)}")
                Row {
                    Text("Cash: ${formatDouble(cash)}", fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Invest: ${formatDouble(invest)}", fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Debt: ${formatDouble(debt)}", fontSize = 11.sp)
                }
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
    MultiChoiceSegmentedButtonRow(modifier = modifier) {
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
                    Text(cappedName, fontSize = 7.sp)
                })
        }
    }
}