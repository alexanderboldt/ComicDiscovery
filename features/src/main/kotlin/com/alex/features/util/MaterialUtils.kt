package com.alex.features.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alex.features.ui.theme.BrightGray
import com.alex.features.ui.theme.DarkCharcoal
import com.alex.features.ui.theme.DarkElectricBlue
import com.alex.features.ui.theme.UltramarineBlue

/**
 * Checks the current theme (light- or dark-mode) and returns the needed color.
 *
 * @param lightColor The color if light-mode is activated.
 * @param darkColor The color if dark-mode is activated.
 */
@Composable
fun getColor(lightColor: Color, darkColor: Color) = if (MaterialTheme.colors.isLight) lightColor else darkColor

/**
 * Returns a set of colors for a Text-Composable, depending on light- or dark-theme.
 */
@Composable
fun getTextFieldColors() = if (MaterialTheme.colors.isLight) {
    TextFieldDefaults.textFieldColors(
        // background
        backgroundColor = BrightGray,
        // icon
        leadingIconColor = UltramarineBlue,
        // label
        unfocusedLabelColor = DarkElectricBlue,
        focusedLabelColor = UltramarineBlue,
        // text
        textColor = DarkCharcoal,
        // indicator
        unfocusedIndicatorColor = DarkElectricBlue,
        focusedIndicatorColor = UltramarineBlue,
        // cursor
        cursorColor = DarkElectricBlue
    )
} else {
    TextFieldDefaults.textFieldColors(
        // background
        backgroundColor = DarkCharcoal,
        // icon
        leadingIconColor = BrightGray,
        // label
        unfocusedLabelColor = BrightGray,
        focusedLabelColor = BrightGray,
        // text
        textColor = BrightGray,
        // indicator
        unfocusedIndicatorColor = BrightGray,
        focusedIndicatorColor = BrightGray,
        // cursor
        cursorColor = BrightGray
    )
}