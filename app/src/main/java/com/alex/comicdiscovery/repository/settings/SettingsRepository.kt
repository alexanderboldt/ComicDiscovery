package com.alex.comicdiscovery.repository.settings

import com.alex.comicdiscovery.repository.datastore.SettingsDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme

class SettingsRepository(private val settingsDataStore: SettingsDataStore) {

    fun getTheme() = settingsDataStore.getTheme()

    suspend fun setTheme(theme: RpModelTheme) {
        return settingsDataStore.setTheme(theme)
    }
}