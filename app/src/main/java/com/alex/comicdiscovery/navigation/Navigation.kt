package com.alex.comicdiscovery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun ComicDiscoveryNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = bottomScreens.first().route) {
        allScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments,
                content = screen.getContent(navController))
        }
    }
}