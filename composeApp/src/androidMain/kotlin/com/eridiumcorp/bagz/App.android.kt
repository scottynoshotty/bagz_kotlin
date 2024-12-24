package com.eridiumcorp.bagz

import androidx.compose.runtime.Composable
import com.eridiumcorp.bagz.components.Bagz
import com.eridiumcorp.bagz.components.ui.theme.BagzTheme

@Composable
actual fun App() = BagzTheme { Bagz() }