package com.eridiumcorp.bagz.components.link

import com.plaid.link.configuration.LinkTokenConfiguration

data class LinkHostUiState(
    val loading: Boolean = true,
    val linkTokenConfiguration: LinkTokenConfiguration? = null,
)
