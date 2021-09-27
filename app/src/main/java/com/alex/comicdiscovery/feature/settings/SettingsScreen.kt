package com.alex.comicdiscovery.feature.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.settings.model.UiModelThemes
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import com.alex.comicdiscovery.ui.theme.Blue500
import com.alex.comicdiscovery.ui.theme.Grey300
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = getViewModel()

    Column(modifier = Modifier
        .background(AlmostWhite)
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.settings_theme_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ThemeButton(UiModelThemes.SYSTEM, viewModel.theme, R.string.settings_theme_system)
            ThemeButton(UiModelThemes.LIGHT, viewModel.theme, R.string.settings_theme_light)
            ThemeButton(UiModelThemes.DARK, viewModel.theme, R.string.settings_theme_dark)
        }
    }
}

@Composable
fun ThemeButton(theme: UiModelThemes, selectedTheme: UiModelThemes, @StringRes text: Int) {
    val viewModel: SettingsViewModel = getViewModel()

    OutlinedButton(
        onClick = { viewModel.onSelectTheme(theme) },
        border = BorderStroke(1.dp, if (theme == selectedTheme) Blue500 else Grey300)) {
        Text(text = stringResource(text), modifier = Modifier.padding(8.dp))
    }
}