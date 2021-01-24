package com.alex.comicdiscovery.repository.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.alex.comicdiscovery.repository.models.RpModelTheme
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val context: Context) {

    private var dataStore: DataStore<Preferences>? = null

    private val keyTheme = preferencesKey<Int>("key_app_theme")

    // ----------------------------------------------------------------------------

    fun init(userIndicator: String) {
        dataStore = context.createDataStore("${userIndicator}_settings_data_store")
    }

    fun clear() {
        dataStore = null
    }

    // ----------------------------------------------------------------------------

    fun getTheme() = dataStore!!
        .data
        .map { it[keyTheme] ?: 0 }
        .map { RpModelTheme.values()[it] }

    suspend fun setTheme(theme: RpModelTheme) {
        dataStore!!.edit { it[keyTheme] = theme.ordinal }
    }
}