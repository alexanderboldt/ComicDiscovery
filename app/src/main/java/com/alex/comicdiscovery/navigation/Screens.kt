package com.alex.comicdiscovery.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.character.detail.CharacterDetailScreen
import com.alex.comicdiscovery.feature.character.overview.CharacterOverviewScreen
import com.alex.comicdiscovery.feature.character.starred.CharacterStarredScreen
import com.alex.comicdiscovery.feature.home.HomeScreen
import com.alex.comicdiscovery.feature.image.ImageScreen
import com.alex.comicdiscovery.feature.settings.SettingsScreen
import com.alex.comicdiscovery.ui.theme.AbsoluteZero
import com.alex.comicdiscovery.ui.theme.Black
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
object Home : Screen {
    override val route = "home"

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        HomeScreen(navControllerTopLevel as NavHostController, navControllerBottomNavigation as NavHostController)
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
object CharacterOverview : BottomScreen {
    override val route = "character_overview"

    override val systemBarsColor = AbsoluteZero to Black

    override val title = "Search"
    override val icon = Icons.Rounded.Search

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        CharacterOverviewScreen { id ->
            navControllerBottomNavigation.navigate(CharacterDetail.createRoute(id, false))
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
object CharacterStarred : BottomScreen {
    override val route = "character_starred"

    override val systemBarsColor = AbsoluteZero to Black

    override val title = "Starred"
    override val icon = Icons.Rounded.Star

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        CharacterStarredScreen { id ->
            navControllerBottomNavigation.navigate(CharacterDetail.createRoute(id, true))
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
object Settings : BottomScreen {
    override val route = "settings"

    override val systemBarsColor = AbsoluteZero to Black

    override val title = "Settings"
    override val icon = Icons.Rounded.Settings

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        SettingsScreen()
    }
}

@ExperimentalCoilApi
object CharacterDetail : Screen {
    private val id = navArgument("id") { type = NavType.IntType }
    private val isStarred = navArgument("is_starred") { type = NavType.BoolType }

    override val route = "character_detail/{${id.name}}?${isStarred.name}={${isStarred.name}}"
    fun createRoute(id: Int, userComesFromStarredScreen: Boolean) = "character_detail/$id?${isStarred.name}=$userComesFromStarredScreen"

    override val arguments = listOf(id, isStarred)

    override val systemBarsColor = AbsoluteZero to Black

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        CharacterDetailScreen(
            it.arguments!!.getInt(id.name),
            it.arguments!!.getBoolean(isStarred.name)) { url ->
            navControllerTopLevel.navigate(Image.createRoute(URLEncoder.encode(url, StandardCharsets.UTF_8.toString())))
        }
    }
}

@ExperimentalCoilApi
object Image : Screen {
    private val url = navArgument("url") { type = NavType.StringType }

    override val route = "image/{${url.name}}"
    fun createRoute(url: String) = "image/$url"

    override val arguments = listOf(url)

    override val systemBarsColor = Black to Black

    override fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit = {
        ImageScreen(it.arguments!!.getString(url.name)!!)
    }
}

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
val topLevelScreens: List<Screen> = listOf(Home, Image)

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
val bottomScreens: List<BottomScreen> = listOf(CharacterOverview, CharacterStarred, Settings)

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
val allBottomScreens: List<Screen> = listOf(CharacterOverview, CharacterStarred, Settings, CharacterDetail)