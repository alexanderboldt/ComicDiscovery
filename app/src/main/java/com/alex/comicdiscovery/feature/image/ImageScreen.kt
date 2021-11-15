package com.alex.comicdiscovery.feature.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.ui.theme.Black

@ExperimentalCoilApi
@Composable
fun ImageScreen(url: String) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = Modifier.background(Black).fillMaxSize()
    )
}