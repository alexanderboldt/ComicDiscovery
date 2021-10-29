package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.ui.theme.BrightGray
import com.alex.comicdiscovery.ui.theme.DarkElectricBlue
import com.alex.comicdiscovery.ui.theme.UltramarineBlue

@Composable
fun ComicDiscoveryButton(text: String, border: BorderStroke? = null, onClick: () -> Unit) {
    Button(
        border = border,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (MaterialTheme.colors.isLight) UltramarineBlue else DarkElectricBlue,
            contentColor = BrightGray
        )) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}