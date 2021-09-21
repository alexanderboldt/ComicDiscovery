package com.alex.comicdiscovery.repository.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.alex.comicdiscovery.repository.models.RpModelTheme
import kotlinx.coroutines.flow.map

class SettingsDataStore(context: Context) {

    private val dataStore = context.createDataStore("user_settings")

    private val keyTheme = intPreferencesKey("APP_THEME")

    // ----------------------------------------------------------------------------

    fun getTheme() = dataStore
        .data
        .map { it[keyTheme] ?: 0 }
        .map { RpModelTheme.values()[it] }

    suspend fun setTheme(theme: RpModelTheme) {
        dataStore.edit { it[keyTheme] = theme.ordinal }
    }
}