package com.alex.comicdiscovery.feature.settings

import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.settings.model.State
import com.alex.repository.models.RpModelTheme
import com.alex.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : BaseViewModel<State, Unit>(State()) {

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository
                .getTheme()
                .collect { state.theme = State.UiModelTheme.valueOf(it.ordinal) }
        }
    }

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: State.UiModelTheme) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.setTheme(RpModelTheme.values()[theme.ordinal])
            state.theme = theme
        }
    }
}