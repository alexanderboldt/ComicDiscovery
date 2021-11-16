package com.alex.comicdiscovery.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ComicDiscoveryTopLevelNavigation(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    NavHost(navControllerTopLevel, topLevelScreens.first().route) {
        topLevelScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments) {
                screen.SetSystemBarsColor()
                screen.getContent(navControllerTopLevel, navControllerBottomNavigation)(it)
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ComicDiscoveryBottomNavigation(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    NavHost(navControllerBottomNavigation, allBottomScreens.first().route) {
        allBottomScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments) {
                screen.SetSystemBarsColor()
                screen.getContent(navControllerTopLevel, navControllerBottomNavigation)(it)
            }
        }
    }
}