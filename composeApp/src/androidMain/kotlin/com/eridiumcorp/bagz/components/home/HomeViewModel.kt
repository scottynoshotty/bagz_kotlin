package com.eridiumcorp.bagz.components.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.models.Report
import com.eridiumcorp.bagz.app.repositories.ReportsRepository
import com.eridiumcorp.bagz.app.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(val authService: AuthService, val reportsRepository: ReportsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reportsRepository.getReport().collect { report ->
                _uiState.value = _uiState.value.copy(
                    report = report,
                    loading = false,
                    graphValues = calculateGraphValues(
                        report,
                        _uiState.value.reportTimePeriod,
                        _uiState.value.reportHoldingsType
                    ) // Calculate and update graphValues here
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }

    private fun calculateGraphValues(
        report: Report,
        timePeriod: ReportTimePeriod,
        holdingsType: ReportHoldingsType,
    ): List<Double> {
        return when (timePeriod) {
            ReportTimePeriod.WEEK -> report.getWeeklyHoldings()
            ReportTimePeriod.MONTH -> report.getMonthlyHoldings()
            ReportTimePeriod.YEAR -> report.getYearlyHoldings()
        }.mapNotNull { holdings ->
            when (holdingsType) {
                ReportHoldingsType.NET -> holdings?.holdingsData?.net
                ReportHoldingsType.CASH -> holdings?.holdingsData?.cash
                ReportHoldingsType.INVESTMENTS -> holdings?.holdingsData?.investments
                ReportHoldingsType.DEBT -> holdings?.holdingsData?.debt
            }
        }
    }

    fun setReportTimePeriod(timePeriod: ReportTimePeriod) {
        _uiState.update { currentState ->
            currentState.copy(
                reportTimePeriod = timePeriod,
                graphValues = calculateGraphValues(
                    currentState.report!!,
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