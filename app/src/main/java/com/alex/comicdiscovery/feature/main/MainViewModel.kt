package com.alex.comicdiscovery.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.main.models.UiModelThemes
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(settingsRepository: SettingsRepository) : ViewModel() {

    var theme: UiModelThemes by mutableStateOf(UiModelThemes.SYSTEM)
        private set

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository
                .getTheme()
                .catch { throwable ->
                    Timber.w(throwable)
                }.collect { result ->
                    theme = UiModelThemes.values()[result.ordinal]
                }
        }
    }
}