package com.alex.features.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alex.features.ui.theme.BrightGray
import com.alex.features.ui.theme.DarkElectricBlue
import com.alex.features.ui.theme.UltramarineBlue

@Composable
fun ComicDiscoveryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (MaterialTheme.colors.isLight) UltramarineBlue else DarkElectricBlue,
            contentColor = BrightGray
        )) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}