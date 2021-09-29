package com.alex.comicdiscovery.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.character.detail.CharacterDetailScreen
import com.alex.comicdiscovery.feature.character.overview.CharacterOverviewScreen
import com.alex.comicdiscovery.feature.character.starred.CharacterStarredScreen
import com.alex.comicdiscovery.feature.image.ImageScreen
import com.alex.comicdiscovery.feature.settings.SettingsScreen

@ExperimentalComposeUiApi
@ExperimentalCoilApi
sealed class Screen(val route: String) {

    sealed class BottomScreen(
        route: String,
        val title: String,
        val icon: ImageVector,
        val content: @Composable (NavHostController, NavBackStackEntry) -> Unit) : Screen(route) {

        object CharacterOverview : BottomScreen("character_overview", "Search", Icons.Filled.Face, { navController, backStackEntry ->
            CharacterOverviewScreen { id ->
                navController.navigate(CharacterDetail.createRoute(id, false))
            }
        })

        object CharacterStarred : BottomScreen("character_starred", "Starred", Icons.Filled.Face, { navController, backStackEntry ->
            CharacterStarredScreen { id ->
                navController.navigate(CharacterDetail.createRoute(id, true))
            }
        })

        object Settings : BottomScreen("settings", "Settings", Icons.Filled.Face, { navController, backStackEntry ->
            SettingsScreen()
        })
    }

    object CharacterDetail : Screen("character_detail/{id}?starred={starred}") {
        fun createRoute(id: Int, userComesFromStarredScreen: Boolean) = "character_detail/$id?starred=$userComesFromStarredScreen"
    }

    object Image : Screen("image/{url}") {
        fun createRoute(url: String) = "image/$url"
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
val bottomScreens = listOf(
    Screen.BottomScreen.CharacterOverview,
    Screen.BottomScreen.CharacterStarred,
    Screen.BottomScreen.Settings
)

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun ComicDiscoveryNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = bottomScreens.first().route) {
        bottomScreens.forEach { bottomScreen ->
            composable(bottomScreen.route) { backStackEntry -> bottomScreen.content(navController, backStackEntry) }
        }

        composable(
            Screen.CharacterDetail.route,
            listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("starred") { type = NavType.BoolType })) {
            CharacterDetailScreen(
                it.arguments!!.getInt("id"),
                it.arguments!!.getBoolean("starred")) { url ->
                navController.navigate(Screen.Image.createRoute(url))
            }
        }

        composable(Screen.Image.route) {
            ImageScreen(it.arguments!!.getString("url")!!)
        }
    }
}