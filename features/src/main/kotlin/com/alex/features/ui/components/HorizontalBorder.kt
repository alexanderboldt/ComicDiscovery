package com.alex.features.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalBorder(modifier: Modifier = Modifier) {
    Spacer(modifier.height(1.dp).fillMaxWidth())
}