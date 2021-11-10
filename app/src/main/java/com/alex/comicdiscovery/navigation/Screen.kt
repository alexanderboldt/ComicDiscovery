package com.alex.comicdiscovery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

sealed interface Screen {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    fun getContent(navControllerTopLevel: NavController, navControllerBottomNavigation: NavController): @Composable (NavBackStackEntry) -> Unit
}