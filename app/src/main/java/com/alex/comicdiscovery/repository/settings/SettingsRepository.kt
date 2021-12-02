package com.alex.comicdiscovery.repository.settings

import com.alex.comicdiscovery.repository.datasource.datastore.SettingsDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.util.mapping.toDsModel
import com.alex.comicdiscovery.util.mapping.toRpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Manages the settings.
 *
 * @param settingsDataStore An instance of [SettingsDataStore].
 */
class SettingsRepository(private val settingsDataStore: SettingsDataStore) {

    /**
     * Returns the saved theme in the DataStore.
     *
     * @return Returns [RpModelTheme] in a [kotlinx.coroutines.flow.Flow].
     */
    fun getTheme() = settingsDataStore
        .getTheme()
        .map { it.toRpModel() }
        .flowOn(Dispatchers.IO)

    /**
     * Sets a theme to the DataStore.
     *
     * @param theme The theme as [RpModelTheme].
     */
    suspend fun setTheme(theme: RpModelTheme) = settingsDataStore.setTheme(theme.toDsModel())
}