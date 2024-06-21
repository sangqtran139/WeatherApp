package com.sangtq.weatherdetail.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sangtq.weatherdetail.WeatherDetailRoute

const val WEATHER_DETAIL_ROUTE = "weather_detail_route"

fun NavController.navigateWeatherDetail(navOptions: NavOptions? = null) =
    navigate(WEATHER_DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.weatherDetailScreen(
    onClickBack: (() -> Unit)? = null
) {
    composable(route = WEATHER_DETAIL_ROUTE) {
        EnterAnimation {
            WeatherDetailRoute(onClickBack = onClickBack)
        }
    }
}

@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}
