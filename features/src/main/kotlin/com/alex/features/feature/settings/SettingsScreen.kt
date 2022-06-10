package com.alex.features.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.features.feature.settings.model.State
import com.alex.features.R
import com.alex.features.ui.components.ComicDiscoverySwitcher
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Destination(route = "SettingsScreen")
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = getViewModel()

    Column(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
    ) {
        val textStyle = TextStyle(
            color = getColor(DarkElectricBlue, BrightGray),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stringResource(R.string.settings_theme_title),
            style = textStyle,
            modifier = Modifier.padding(16.dp)
        )

        ComicDiscoverySwitcher(
            modifier = Modifier.padding(8.dp, 4.dp),
            options = listOf(
                stringResource(id = R.string.settings_theme_system),
                stringResource(id = R.string.settings_theme_light),
                stringResource(id = R.string.settings_theme_dark)
            ),
            selected = viewModel.state.theme.ordinal
        ) { newIndex ->
            viewModel.onSelectTheme(State.UiModelTheme.valueOf(newIndex))
        }
    }
}