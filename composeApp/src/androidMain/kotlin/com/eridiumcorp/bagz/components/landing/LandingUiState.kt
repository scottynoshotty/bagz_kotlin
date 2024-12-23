package com.eridiumcorp.bagz.components.landing

import com.eridiumcorp.bagz.app.models.User

data class LandingUiState(val loading: Boolean = true, val user: User? = null)
