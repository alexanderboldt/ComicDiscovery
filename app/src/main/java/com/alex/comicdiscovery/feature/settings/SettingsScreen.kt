package com.alex.comicdiscovery.feature.settings

import androidx.annotation.StringRes
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
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = getViewModel()) {
    Column(modifier = Modifier
        .background(AlmostWhite)
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.settings_theme_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ThemeButton(viewModel, UiModelThemes.SYSTEM, R.string.settings_theme_system)
            ThemeButton(viewModel, UiModelThemes.LIGHT, R.string.settings_theme_light)
            ThemeButton(viewModel, UiModelThemes.DARK, R.string.settings_theme_dark)
        }
    }
}

@Composable
fun ThemeButton(viewModel: SettingsViewModel, theme: UiModelThemes, @StringRes text: Int) {
    OutlinedButton(onClick = { viewModel.onSelectTheme(theme) }) {
        Text(text = stringResource(text), modifier = Modifier.padding(8.dp))
    }
}