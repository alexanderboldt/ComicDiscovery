package com.alex.comicdiscovery.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.alex.comicdiscovery.feature.home.HomeScreen
import com.alex.comicdiscovery.ui.theme.ComicDiscoveryTheme

@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComicDiscoveryTheme {
                HomeScreen()
            }
        }
    }
}