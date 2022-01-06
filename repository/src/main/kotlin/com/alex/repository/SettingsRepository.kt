package com.alex.repository

import com.alex.datastore.settings.SettingsDataStore
import com.alex.repository.mapping.toDsModel
import com.alex.repository.mapping.toRpModel
import com.alex.repository.model.RpModelTheme
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