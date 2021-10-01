package com.alex.comicdiscovery.repository.settings

import com.alex.comicdiscovery.repository.datasource.datastore.SettingsDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.util.mapping.ThemeMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Manages the settings.
 */
class SettingsRepository(private val settingsDataStore: SettingsDataStore) {

    /**
     * Gets the saved theme from the DataStore.
     *
     * @return Returns a flow with the theme.
     */
    fun getTheme() = settingsDataStore
        .getTheme()
        .map { ThemeMapper.mapDsModelThemeToRpModelTheme(it) }
        .flowOn(Dispatchers.IO)

    /**
     * Sets a theme to the DataStore.
     *
     * @param theme The theme to store.
     */
    suspend fun setTheme(theme: RpModelTheme) = settingsDataStore
        .setTheme(ThemeMapper.mapRpModelThemeToDsModelTheme(theme))
}