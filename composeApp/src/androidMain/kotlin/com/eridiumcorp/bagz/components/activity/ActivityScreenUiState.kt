package com.eridiumcorp.bagz.components.activity

import com.eridiumcorp.bagz.app.models.Activity

data class ActivityScreenUiState(
    val loading: Boolean = false,
    val activity: Activity? = null,
)
