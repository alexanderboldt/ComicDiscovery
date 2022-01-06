package com.alex.features.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alex.features.ui.theme.BrightGray
import com.alex.features.ui.theme.DarkElectricBlue
import com.alex.features.ui.theme.UltramarineBlue

@Composable
fun ComicDiscoveryOutlinedButton(text: String, modifier: Modifier = Modifier, isEnabled: Boolean, onClick: () -> Unit) {
    val color = if (MaterialTheme.colors.isLight) UltramarineBlue else BrightGray

    OutlinedButton(
        onClick = { if (isEnabled) onClick() },
        modifier = modifier,
        border = BorderStroke(1.dp, if (isEnabled) color else DarkElectricBlue),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = if (isEnabled) color else DarkElectricBlue
        )) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}