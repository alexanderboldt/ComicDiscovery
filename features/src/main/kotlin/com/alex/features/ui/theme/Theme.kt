package com.alex.features.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ComicDiscoveryTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colors = if (isDarkTheme) darkColors() else lightColors(),
            typography = if (isDarkTheme) DarkTypography else LightTypography,
            shapes = Shapes,
            content = content
        )
    }
}