package com.alex.comicdiscovery.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.settings.model.UiModelTheme
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : BaseViewModel<Unit, Unit>(Unit) {

    var theme: UiModelTheme by mutableStateOf(UiModelTheme.SYSTEM)
        private set

    // ----------------------------------------------------------------------------
    
    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository
                .getTheme()
                .collect { theme = UiModelTheme.valueOf(it.ordinal) }
        }
    }

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: UiModelTheme) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.setTheme(RpModelTheme.values()[theme.ordinal])
            this@SettingsViewModel.theme = theme
        }
    }
}