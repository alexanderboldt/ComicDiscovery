package com.alex.features.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.alex.features.feature.NavGraphs
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.DestinationsNavHost

data class BottomNavigationItem(val route: String, val title: String, val icon: ImageVector)

val bottomNavigationItems = listOf(
    BottomNavigationItem("CharacterOverviewScreen", "Search", Icons.Rounded.Search),
    BottomNavigationItem("CharacterStarredScreen", "Starred", Icons.Rounded.Star),
    BottomNavigationItem("ProfileScreen", "User", Icons.Rounded.AccountBox),
    BottomNavigationItem("SettingsScreen", "Settings", Icons.Rounded.Settings)
)

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = getColor(UltramarineBlue, ChineseBlack),
                contentColor = BrightGray
            ) {
                bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                    BottomNavigationItem(
                        selected = viewModel.selectNavigationIndex == index,
                        onClick = {
                            navController.navigate(bottomNavigationItem.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.id)
                            }
                            viewModel.onClickNavigationItem(index)
                        },
                        label = { Text(bottomNavigationItem.title) },
                        icon = { Icon(bottomNavigationItem.icon, null) })
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
        }
    }
}