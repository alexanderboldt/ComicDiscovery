package com.alex.comicdiscovery.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.repository.session.SessionRepository
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsViewModel(
    private val sessionRepository: SessionRepository,
    private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _userState = MutableLiveData<String>()
    val userState: LiveData<String> = _userState

    private val _themeState = MutableLiveData<UiModelThemes>()
    val themeState: LiveData<UiModelThemes> = _themeState

    private val _loginScreenState = LiveEvent<Unit>()
    val loginScreenState: LiveData<Unit> = _loginScreenState

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            sessionRepository
                .getUsername()
                .catch { Timber.w(it) }
                .collect { _userState.postValue(it) }
        }
    }

    // ----------------------------------------------------------------------------

    fun onSelectTheme(theme: UiModelThemes) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.setTheme(RpModelTheme.values()[theme.ordinal])
        }
    }

    fun onClickLogout() {
        viewModelScope.launch(Dispatchers.Main) {
            sessionRepository.clear()
            _loginScreenState.postValue(Unit)
        }
    }
}