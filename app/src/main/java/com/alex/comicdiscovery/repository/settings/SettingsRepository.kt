package com.alex.comicdiscovery.repository.settings

import com.alex.comicdiscovery.repository.datasource.datastore.SettingsDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.util.mapping.ThemeMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SettingsRepository(private val settingsDataStore: SettingsDataStore) {

    fun getTheme() = settingsDataStore
        .getTheme()
        .map { ThemeMapper.mapDsModelThemeToRpModelTheme(it) }
        .flowOn(Dispatchers.IO)

    suspend fun setTheme(theme: RpModelTheme) = settingsDataStore
        .setTheme(ThemeMapper.mapRpModelThemeToDsModelTheme(theme))
}