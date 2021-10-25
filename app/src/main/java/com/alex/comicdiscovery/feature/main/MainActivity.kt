package com.alex.comicdiscovery.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.main.model.UiModelThemes
import com.alex.comicdiscovery.navigation.ComicDiscoveryTopLevelNavigation
import com.alex.comicdiscovery.ui.theme.Blue700
import com.alex.comicdiscovery.ui.theme.ComicDiscoveryTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // change the color of the StatusBar and NavigationBar
            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(Blue700, false)
            }

            // setup the theme
            val viewModel: MainViewModel = getViewModel()

            val isDarkTheme = when(viewModel.theme) {
                UiModelThemes.LIGHT -> false
                UiModelThemes.DARK -> true
                UiModelThemes.SYSTEM -> isSystemInDarkTheme()
            }

            // the content
            ComicDiscoveryTheme(isDarkTheme) {
                val navControllerTopLevel = rememberNavController()
                val navControllerBottomNavigation = rememberNavController()

                ComicDiscoveryTopLevelNavigation(navControllerTopLevel, navControllerBottomNavigation)
            }
        }
    }
}