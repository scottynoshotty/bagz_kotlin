package com.eridiumcorp.bagz.components.home.widgets.activity

import com.eridiumcorp.bagz.app.models.Activity

data class ActivityWidgetUiState(
    val loading: Boolean = false,
    val activity: Activity? = null,
)
