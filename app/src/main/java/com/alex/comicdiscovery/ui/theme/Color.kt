package com.alex.comicdiscovery.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val AlmostWhite = Color(0xffefefef)

val Blue300 = Color(0xff8187ff)
val Blue500 = Color(0xff3d5afe)
val Blue700 = Color(0xff0031ca)

val Grey300 = Color(0xff62727b)
val Grey500 = Color(0xff37474f)
val Grey700 = Color(0xff333333)
val AlmostBlack = Color(0xff102027)

val DarkColorPalette = darkColors(
    primary = Blue300,
    primaryVariant = Blue700,
    secondary = Grey300
)

val LightColorPalette = lightColors(
    primary = Blue300,
    primaryVariant = Blue700,
    secondary = Grey300

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)