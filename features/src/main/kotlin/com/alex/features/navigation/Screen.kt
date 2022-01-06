package com.alex.features.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.alex.features.ui.theme.AbsoluteZero
import com.alex.features.ui.theme.Black
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalAnimationApi
sealed interface Screen {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val enterTransition: AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition
        get() = { _, _ -> fadeIn(animationSpec = tween(300)) }

    val exitTransition: AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition
        get() = { _, _ -> fadeOut(animationSpec = tween(300)) }

    val popEnterTransition: AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition
        get() = { _, _ -> fadeIn(animationSpec = tween(300)) }

    val popExitTransition: AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition
        get() = { _, _ -> fadeOut(animationSpec = tween(300)) }

    // first color is for light-theme, second color is for dark-theme
    val systemBarsColor: Pair<Color, Color>?
        get() = AbsoluteZero to Black

    @Composable
    fun SetSystemBarsColor() {
        val systemUiController = rememberSystemUiController()

        systemBarsColor?.also { (lightColor, darkColor) ->
            val color = if (MaterialTheme.colors.isLight) lightColor else darkColor
            LaunchedEffect(Unit) { systemUiController.setSystemBarsColor(color) }
        }
    }

    fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit
}