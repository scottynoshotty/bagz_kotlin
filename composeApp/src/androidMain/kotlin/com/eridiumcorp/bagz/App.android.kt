package com.eridiumcorp.bagz

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.eridiumcorp.bagz.components.Bagz

@Composable
actual fun App() = MaterialTheme { Bagz() }