package com.alex.features.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.ExperimentalComposeUiApi
import com.alex.features.R
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.base.ResourceProvider
import com.alex.features.feature.destinations.CharacterOverviewScreenDestination
import com.alex.features.feature.destinations.CharacterStarredScreenDestination
import com.alex.features.feature.destinations.ProfileScreenDestination
import com.alex.features.feature.destinations.SettingsScreenDestination
import com.alex.features.feature.home.model.State

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class HomeViewModel(resourceProvider: ResourceProvider) : BaseViewModel<State, Unit>(State()) {

    private val bottomNavigationItems = listOf(
        State.BottomNavigationItem(
            CharacterOverviewScreenDestination.route,
            resourceProvider.getString(R.string.bottom_navigation_page_search),
            Icons.Rounded.Search
        ),
        State.BottomNavigationItem(
            CharacterStarredScreenDestination.route,
            resourceProvider.getString(R.string.bottom_navigation_page_starred),
            Icons.Rounded.Star
        ),
        State.BottomNavigationItem(
            ProfileScreenDestination.route,
            resourceProvider.getString(R.string.bottom_navigation_page_user),
            Icons.Rounded.AccountBox
        ),
        State.BottomNavigationItem(
            SettingsScreenDestination.route,
            resourceProvider.getString(R.string.bottom_navigation_page_settings),
            Icons.Rounded.Settings
        )
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