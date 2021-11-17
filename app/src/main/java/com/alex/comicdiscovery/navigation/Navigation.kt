package com.alex.comicdiscovery.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ComicDiscoveryTopLevelNavigation(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    AnimatedNavHost(navControllerTopLevel, topLevelScreens.first().route) {
        topLevelScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments,
                enterTransition = screen.enterTransition,
                exitTransition = screen.exitTransition,
                popEnterTransition = screen.popEnterTransition,
                popExitTransition = screen.popExitTransition) {
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
    AnimatedNavHost(navControllerBottomNavigation, allBottomScreens.first().route) {
        allBottomScreens.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.arguments,
                enterTransition = screen.enterTransition,
                exitTransition = screen.exitTransition,
                popEnterTransition = screen.popEnterTransition,
                popExitTransition = screen.popExitTransition) {
                screen.SetSystemBarsColor()
                screen.getContent(navControllerTopLevel, navControllerBottomNavigation)(it)
            }
        }
    }
}