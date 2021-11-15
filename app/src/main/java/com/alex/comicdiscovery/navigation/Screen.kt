package com.alex.comicdiscovery.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.alex.comicdiscovery.ui.theme.AbsoluteZero
import com.alex.comicdiscovery.ui.theme.Black
import com.google.accompanist.systemuicontroller.rememberSystemUiController

sealed interface Screen {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

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