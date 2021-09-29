package com.alex.comicdiscovery.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.settings.model.UiModelThemes
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    var theme: UiModelThemes by mutableStateOf(UiModelThemes.SYSTEM)
        private set

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: UiModelThemes) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.setTheme(RpModelTheme.values()[theme.ordinal])
            this@SettingsViewModel.theme = theme
        }
    }
}