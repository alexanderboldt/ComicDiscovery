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
                navController.navigate(CharacterDetail.createRoute(id))
            }
        })

        object CharacterStarred : BottomScreen("character_starred", "Starred", Icons.Filled.Face, { navController, backStackEntry ->
            CharacterStarredScreen { id ->
                navController.navigate(CharacterDetail.createRoute(id))
            }
        })

        object Settings : BottomScreen("settings", "Settings", Icons.Filled.Face, { navController, backStackEntry ->
            SettingsScreen()
        })
    }

    object CharacterDetail : Screen("character_detail/{id}") {
        fun createRoute(id: Int) = "character_detail/$id"
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

        composable(Screen.CharacterDetail.route) {
            CharacterDetailScreen(it.arguments!!.getString("id")!!.toInt() ) { url ->
                navController.navigate(Screen.Image.createRoute(url))
            }
        }

        composable(Screen.Image.route) {
            ImageScreen(it.arguments!!.getString("url")!!)
        }
    }
}