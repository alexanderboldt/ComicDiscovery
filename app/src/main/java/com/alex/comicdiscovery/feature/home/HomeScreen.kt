package com.alex.comicdiscovery.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.navigation.ComicDiscoveryNavigation
import com.alex.comicdiscovery.navigation.bottomScreens

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                bottomScreens.forEachIndexed { index, bottomScreen ->
                    BottomNavigationItem(
                        selected = selectedItem == index,
                        onClick = {
                            navController.navigate(bottomScreen.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.id)
                            }
                            selectedItem = index
                        },
                        label = { Text(bottomScreen.title) },
                        icon = { Icon(bottomScreen.icon, null) })
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            ComicDiscoveryNavigation(navController = navController)
        }
    }
}