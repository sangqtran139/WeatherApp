package com.sangtq.weatherapp.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sangtq.weatherapp.NiaAppState

@Composable
fun NiaNavHost(
    appState: NiaAppState,
    modifier: Modifier,
    startDestination: String = WEATHER_HOME_ROUTE
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        weatherHomeScreen()
    }
}