package com.alex.comicdiscovery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun ComicDiscoveryTopLevelNavigation(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    NavHost(navControllerTopLevel, topLevelScreens.first().route) {
        topLevelScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments,
                content = screen.getContent(navControllerTopLevel, navControllerBottomNavigation)
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun ComicDiscoveryBottomNavigation(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    NavHost(navControllerBottomNavigation, allBottomScreens.first().route) {
        allBottomScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments,
                content = screen.getContent(navControllerTopLevel, navControllerBottomNavigation))
        }
    }
}