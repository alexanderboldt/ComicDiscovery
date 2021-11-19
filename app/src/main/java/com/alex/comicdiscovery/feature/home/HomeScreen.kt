package com.alex.comicdiscovery.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.navigation.*
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.util.getColor

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(navControllerTopLevel: NavHostController, navControllerBottomNavigation: NavHostController) {
    val viewModel: HomeViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = getColor(UltramarineBlue, ChineseBlack),
                contentColor = BrightGray) {
                bottomScreens.forEachIndexed { index, bottomScreen ->
                    BottomNavigationItem(
                        selected = viewModel.selectNavigationIndex == index,
                        onClick = {
                            navControllerBottomNavigation.navigate(bottomScreen.route) {
                                launchSingleTop = true
                                popUpTo(navControllerBottomNavigation.graph.id)
                            }
                            viewModel.onClickNavigationItem(index)
                        },
                        label = { Text(bottomScreen.title) },
                        icon = { Icon(bottomScreen.icon, null) })
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            ComicDiscoveryBottomNavigation(navControllerTopLevel, navControllerBottomNavigation)
        }
    }
}