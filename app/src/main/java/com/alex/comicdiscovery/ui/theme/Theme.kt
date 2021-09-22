package com.alex.comicdiscovery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ComicDiscoveryTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isDarkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content)
}