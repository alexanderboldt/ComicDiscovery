package com.alex.comicdiscovery.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.main.model.UiModelTheme
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    var theme: UiModelTheme by mutableStateOf(UiModelTheme.SYSTEM)
        private set

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository
                .getTheme()
                .collect { result ->
                    theme = UiModelTheme.values()[result.ordinal]
                }
        }
    }
}