package com.alex.comicdiscovery.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Checks the current theme (light- or dark-mode) and returns the needed color.
 *
 * @param lightColor The color if light-mode is activated.
 * @param darkColor The color if dark-mode is activated.
 */
@Composable
fun getColor(lightColor: Color, darkColor: Color) = if (MaterialTheme.colors.isLight) lightColor else darkColor