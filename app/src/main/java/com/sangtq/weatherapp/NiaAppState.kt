package com.sangtq.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNiaAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): NiaAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        NiaAppState(
            navController = navController
        )
    }
}

@Stable
class NiaAppState(
    val navController: NavHostController
)