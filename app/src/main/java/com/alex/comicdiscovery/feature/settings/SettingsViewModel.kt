package com.alex.comicdiscovery.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _themeState = MutableLiveData<UiModelThemes>()
    val themeState: LiveData<UiModelThemes> = _themeState

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: UiModelThemes) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository
                .setTheme(RpModelTheme.values()[theme.ordinal])
                .catch { throwable ->
                    Timber.w(throwable)
                }.collect { result ->
                    if (result) _themeState.postValue(theme)
                }
        }
    }
}