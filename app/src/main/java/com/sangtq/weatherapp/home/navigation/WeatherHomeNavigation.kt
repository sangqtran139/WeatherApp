package com.sangtq.weatherapp.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sangtq.weatherapp.home.WeatherHomeRoute

const val WEATHER_HOME_ROUTE = "weather_home_route"

fun NavController.navigateWeatherHome(navOptions: NavOptions? = null)
    = navigate(WEATHER_HOME_ROUTE, navOptions)

fun NavGraphBuilder.weatherHomeScreen(
) {
    composable(route = WEATHER_HOME_ROUTE) {
        WeatherHomeRoute()
    }
}
