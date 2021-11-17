package com.alex.comicdiscovery.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.graphics.vector.ImageVector

@ExperimentalAnimationApi
sealed interface BottomScreen : Screen {
    val title: String
    val icon: ImageVector
}