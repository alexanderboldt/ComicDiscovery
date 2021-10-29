package com.alex.comicdiscovery.feature.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.settings.model.UiModelTheme
import com.alex.comicdiscovery.ui.components.ComicDiscoveryButton
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.util.getColor
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = getViewModel()

    Column(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {

        val textStyle = TextStyle(
            color = getColor(DarkElectricBlue, BrightGray),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium)

        Text(
            text = stringResource(R.string.settings_theme_title),
            style = textStyle,
            modifier = Modifier.padding(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ThemeButton(UiModelTheme.SYSTEM, viewModel.theme, R.string.settings_theme_system)
            ThemeButton(UiModelTheme.LIGHT, viewModel.theme, R.string.settings_theme_light)
            ThemeButton(UiModelTheme.DARK, viewModel.theme, R.string.settings_theme_dark)
        }
    }
}

@Composable
fun ThemeButton(theme: UiModelTheme, selectedTheme: UiModelTheme, @StringRes text: Int) {
    val viewModel: SettingsViewModel = getViewModel()

    val border = if (theme == selectedTheme) {
        BorderStroke(2.dp, getColor(CoralRed, BrightGray))
    } else {
        null
    }

    ComicDiscoveryButton(stringResource(text), border) {
        viewModel.onSelectTheme(theme)
    }
}