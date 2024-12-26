package com.eridiumcorp.bagz.app.models

data class Report(
    val date: String,
    val net: Double,
    val netDiff: Double,
    val cash: Double,
    val cashDiff: Double,
    val investments: Double,
    val investmentsDiff: Double,
    val debt: Double,
    val debtDiff: Double,
)
