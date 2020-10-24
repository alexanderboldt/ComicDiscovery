package com.alex.comicdiscovery.repository.settings

import android.content.Context
import com.alex.comicdiscovery.repository.datastore.SettingsDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme
import kotlinx.coroutines.flow.*

class SettingsRepository(context: Context) {

    private val settingsDataStore = SettingsDataStore(context)

    // ----------------------------------------------------------------------------

    suspend fun getTheme(): Flow<RpModelTheme> {
        return settingsDataStore.getTheme()
    }

    suspend fun setTheme(theme: RpModelTheme): Flow<Boolean> {
        return settingsDataStore.setTheme(theme)
    }
}