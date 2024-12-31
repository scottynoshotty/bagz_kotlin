package com.eridiumcorp.bagz.components.activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivityScreen(
    activityScreenViewModel: ActivityScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { padding ->
        Text(modifier = Modifier.padding(padding), text = "Hi")
    }
}