package com.alex.comicdiscovery.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes

class SettingsViewModel : ViewModel() {

    private val _themeState = MutableLiveData<UiModelThemes>()
    val themeState: LiveData<UiModelThemes> = _themeState

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: UiModelThemes) {
        _themeState.postValue(theme)
    }
}