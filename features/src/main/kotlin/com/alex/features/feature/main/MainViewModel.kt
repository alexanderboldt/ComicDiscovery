package com.alex.features.feature.main

import androidx.lifecycle.viewModelScope
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.main.model.State
import com.alex.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val settingsRepository: SettingsRepository) : BaseViewModel<State, Unit>(State()) {

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository
                .getTheme()
                .collect { result ->
                    state.theme = State.UiModelTheme.values()[result.ordinal]
                }
        }
    }
}