package com.eridiumcorp.bagz.components.home

import com.eridiumcorp.bagz.app.models.Report

data class BagWidgetUiState(
    val loading: Boolean = true,
    val report: Report? = null,
    val reportTimePeriod: ReportTimePeriod = ReportTimePeriod.WEEK,
    val reportHoldingsType: ReportHoldingsType = ReportHoldingsType.NET,
    val graphLabels: List<String> = emptyList(),
    val graphValues: List<Double> = emptyList(),
)
