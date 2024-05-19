package com.sangtq.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sangtq.weatherapp.home.navigation.NiaNavHost

@Composable
fun NiaApp(appState: NiaAppState, modifier: Modifier) {
    NiaNavHost(appState = appState, modifier = modifier)
}