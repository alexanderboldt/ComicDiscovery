package com.alex.features.feature.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.alex.features.ui.theme.Black
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@ExperimentalCoilApi
@Composable
fun ImageScreen(url: String) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.isSystemBarsVisible = false
    }

    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier.background(Black).fillMaxSize()
    )
}