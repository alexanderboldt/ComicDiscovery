package com.alex.features.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.home.model.State

class HomeViewModel : BaseViewModel<State, Unit>(State()) {

    private val bottomNavigationItems = listOf(
        State.BottomNavigationItem("CharacterOverviewScreen", "Search", Icons.Rounded.Search),
        State.BottomNavigationItem("CharacterStarredScreen", "Starred", Icons.Rounded.Star),
        State.BottomNavigationItem("ProfileScreen", "User", Icons.Rounded.AccountBox),
        State.BottomNavigationItem("SettingsScreen", "Settings", Icons.Rounded.Settings)
    )

    // ----------------------------------------------------------------------------

    init {
        state.bottomNavigationItems = bottomNavigationItems
    }

    // ----------------------------------------------------------------------------

    fun onClickNavigationItem(index: Int) {
        state.selectNavigationIndex = index
    }
}