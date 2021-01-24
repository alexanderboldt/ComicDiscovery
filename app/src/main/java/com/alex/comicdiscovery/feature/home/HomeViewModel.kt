package com.alex.comicdiscovery.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.home.models.UiModelThemes
import com.alex.comicdiscovery.repository.session.SessionRepository
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val sessionRepository: SessionRepository,
    private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _themeState = LiveEvent<UiModelThemes>()
    val themeState: LiveData<UiModelThemes> = _themeState

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sessionRepository.init()

            settingsRepository
                .getTheme()
                .catch { throwable ->
                    Timber.w(throwable)
                }.collect { result ->
                    println("==== $result")
                    _themeState.postValue(UiModelThemes.values()[result.ordinal])
                }
        }
    }
}