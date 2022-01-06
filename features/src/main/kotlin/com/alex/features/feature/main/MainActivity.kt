package com.alex.features.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.annotation.ExperimentalCoilApi
import com.alex.features.feature.main.model.UiModelTheme
import com.alex.features.navigation.ComicDiscoveryTopLevelNavigation
import com.alex.features.ui.theme.AbsoluteZero
import com.alex.features.ui.theme.Black
import com.alex.features.ui.theme.ComicDiscoveryTheme
import com.alex.repository.SettingsRepository
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val viewModel: MainViewModel = getViewModel()

            // setup the theme
            val isDarkTheme = when(viewModel.theme) {
                UiModelTheme.LIGHT -> false
                UiModelTheme.DARK -> true
                UiModelTheme.SYSTEM -> isSystemInDarkTheme()
            }

            // the content
            ComicDiscoveryTheme(isDarkTheme) {
                // change the color of the StatusBar and NavigationBar
                val systemUiController = rememberSystemUiController()

                systemUiController.setSystemBarsColor(if (isDarkTheme) Black else AbsoluteZero)

                val navControllerTopLevel = rememberAnimatedNavController()
                val navControllerBottomNavigation = rememberAnimatedNavController()

                ComicDiscoveryTopLevelNavigation(navControllerTopLevel, navControllerBottomNavigation)
            }
        }
    }
}