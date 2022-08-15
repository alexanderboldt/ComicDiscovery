package com.alex.features.feature.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.alex.features.ui.theme.Black
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ImageScreen(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .background(Black)
            .fillMaxSize()
    )
}