package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.ui.theme.BrightGray
import com.alex.comicdiscovery.ui.theme.DarkElectricBlue
import com.alex.comicdiscovery.ui.theme.UltramarineBlue

@Composable
fun ComicDiscoveryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (MaterialTheme.colors.isLight) UltramarineBlue else DarkElectricBlue,
            contentColor = BrightGray
        )) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}