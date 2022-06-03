package com.alex.features.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.alex.features.feature.home.HomeScreen
import com.alex.features.feature.main.model.State
import com.alex.features.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val viewModel: MainViewModel = getViewModel()

            // setup the theme
            val isDarkTheme = when(viewModel.state.theme) {
                State.UiModelTheme.LIGHT -> false
                State.UiModelTheme.DARK -> true
                State.UiModelTheme.SYSTEM -> isSystemInDarkTheme()
            }

            // the content
            ComicDiscoveryTheme(isDarkTheme) {
                // change the color of the StatusBar and NavigationBar
                rememberSystemUiController().apply {
                    setSystemBarsColor(if (isDarkTheme) Black else AbsoluteZero)
                }

                HomeScreen()
            }
        }
    }
}