package com.alex.comicdiscovery.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alex.comicdiscovery.feature.character.overview.CharacterOverviewScreen

@ExperimentalComposeUiApi
sealed class Screen(val route: String) {

    sealed class BottomScreen(
        route: String,
        val title: String,
        val icon: ImageVector,
        val content: @Composable (NavHostController, NavBackStackEntry) -> Unit) : Screen(route) {

        object CharacterOverview : BottomScreen("character_overview", "Search", Icons.Filled.Face, { navController, backStackEntry ->
            CharacterOverviewScreen()
        })

        object CharacterStarred : BottomScreen("character_starred", "Starred", Icons.Filled.Face, { navController, backStackEntry ->

        })

        object Settings : BottomScreen("settings", "Settings", Icons.Filled.Face, { navController, backStackEntry ->

        })
    }
}

@ExperimentalComposeUiApi
val bottomScreens = listOf(
    Screen.BottomScreen.CharacterOverview,
    Screen.BottomScreen.CharacterStarred,
    Screen.BottomScreen.Settings
)

@ExperimentalComposeUiApi
@Composable
fun ComicDiscoveryNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "character_overview") {
        bottomScreens.forEach { bottomScreen ->
            composable(bottomScreen.route) { backStackEntry -> bottomScreen.content(navController, backStackEntry) }
        }
    }
}