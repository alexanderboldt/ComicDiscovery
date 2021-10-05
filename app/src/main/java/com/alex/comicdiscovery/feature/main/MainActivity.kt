package com.alex.comicdiscovery.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.home.HomeScreen
import com.alex.comicdiscovery.feature.main.model.UiModelThemes
import com.alex.comicdiscovery.ui.theme.ComicDiscoveryTheme
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = getViewModel()

            val isDarkTheme = when(viewModel.theme) {
                UiModelThemes.LIGHT -> false
                UiModelThemes.DARK -> true
                UiModelThemes.SYSTEM -> isSystemInDarkTheme()
            }

            ComicDiscoveryTheme(isDarkTheme) {
                HomeScreen()
            }
        }
    }
}