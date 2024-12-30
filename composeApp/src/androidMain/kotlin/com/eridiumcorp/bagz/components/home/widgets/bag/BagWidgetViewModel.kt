package com.eridiumcorp.bagz.components.home.widgets.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.models.Holdings
import com.eridiumcorp.bagz.app.models.Report
import com.eridiumcorp.bagz.app.repositories.ReportsRepository
import com.eridiumcorp.bagz.app.utils.getAbbreviatedMonthName
import com.eridiumcorp.bagz.app.utils.getDayOfWeekString
import com.eridiumcorp.bagz.app.utils.getMonthDayString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BagWidgetViewModel(val reportsRepository: ReportsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(BagWidgetUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reportsRepository.getReport().collect { report ->
                _uiState.value = _uiState.value.copy(
                    report = report,
                    loading = false,
                    graphLabels = calculateGraphLabels(
                        report,
                        _uiState.value.reportTimePeriod,
                    ),
                    graphValues = calculateGraphValues(
                        report,
                        _uiState.value.reportTimePeriod,
                        _uiState.value.reportHoldingsType
                    ) // Calculate and update graphValues here
                )
            }
        }
    }


    private fun calculateGraphValues(
        report: Report,
        timePeriod: ReportTimePeriod,
        holdingsType: ReportHoldingsType,
    ): List<Double> {
        return getGraphHoldings(report, timePeriod).mapNotNull { holdings ->
            when (holdingsType) {
                ReportHoldingsType.NET -> holdings?.holdingsData?.net
                ReportHoldingsType.CASH -> holdings?.holdingsData?.cash
                ReportHoldingsType.INVESTMENTS -> holdings?.holdingsData?.investments
                ReportHoldingsType.DEBT -> holdings?.holdingsData?.debt
            }
        }
    }

    private fun calculateGraphLabels(
        report: Report,
        timePeriod: ReportTimePeriod,
    ): List<String> {
        return getGraphHoldings(report, timePeriod).mapNotNull { holdings ->
            when (timePeriod) {
                ReportTimePeriod.WEEK -> getDayOfWeekString(holdings?.timestampSeconds ?: 0)
                ReportTimePeriod.MONTH -> getMonthDayString(holdings?.timestampSeconds ?: 0)
                ReportTimePeriod.YEAR -> getAbbreviatedMonthName(holdings?.timestampSeconds ?: 0)
            }
        }
    }

    private fun getGraphHoldings(
        report: Report,
        timePeriod: ReportTimePeriod,
    ): List<Holdings?> {
        return when (timePeriod) {
            ReportTimePeriod.WEEK -> report.getWeeklyHoldings()
            ReportTimePeriod.MONTH -> report.getMonthlyHoldings()
            ReportTimePeriod.YEAR -> report.getYearlyHoldings()
        }
    }

    fun setReportTimePeriod(timePeriod: ReportTimePeriod) {
        _uiState.update { currentState ->
            currentState.copy(
                reportTimePeriod = timePeriod,
                graphLabels = calculateGraphLabels(
                    currentState.report!!,
                    timePeriod,
                ),
                graphValues = calculateGraphValues(
                    currentState.report,
                    timePeriod,
                    currentState.reportHoldingsType
                )
            )
        }
    }

    fun setReportHoldingsType(holdingsType: ReportHoldingsType) {
        _uiState.update { currentState ->
            currentState.copy(
                reportHoldingsType = holdingsType,
                graphValues = calculateGraphValues(
                    currentState.report!!,
                    currentState.reportTimePeriod,
                    holdingsType
                )
            )
        }
    }
}

enum class ReportTimePeriod {
    WEEK,
    MONTH,
    YEAR
}

enum class ReportHoldingsType {
    NET,
    CASH,
    INVESTMENTS,
    DEBT
}