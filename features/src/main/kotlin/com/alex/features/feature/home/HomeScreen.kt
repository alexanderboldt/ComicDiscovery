package com.alex.features.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.alex.features.feature.NavGraphs
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.DestinationsNavHost

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
                viewModel.state.bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                    BottomNavigationItem(
                        selected = viewModel.state.selectNavigationIndex == index,
                        onClick = {
                            navController.navigate(bottomNavigationItem.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.id)
                            }
                            viewModel.onClickNavigationItem(index)
                        },
                        label = { Text(bottomNavigationItem.title) },
                        icon = { Icon(bottomNavigationItem.icon, null) }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
        }
    }
}