package com.alex.features.feature.home.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

class State {

    data class BottomNavigationItem(val route: String, val title: String, val icon: ImageVector)

    // ----------------------------------------------------------------------------

    var bottomNavigationItems: List<BottomNavigationItem> by mutableStateOf(emptyList())
    var selectNavigationIndex: Int by mutableStateOf(0)
}