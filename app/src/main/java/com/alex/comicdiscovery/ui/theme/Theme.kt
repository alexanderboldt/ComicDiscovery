package com.alex.comicdiscovery.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun ComicDiscoveryTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isDarkTheme) darkColors() else lightColors(),
        typography = Typography,
        shapes = Shapes,
        content = content)
}