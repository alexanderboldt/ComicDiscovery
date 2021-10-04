package com.alex.comicdiscovery.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface BottomScreen : Screen {
    val title: String
    val icon: ImageVector
}