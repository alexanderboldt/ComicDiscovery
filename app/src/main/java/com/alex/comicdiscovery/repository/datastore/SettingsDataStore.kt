package com.alex.comicdiscovery.repository.datastore

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.alex.comicdiscovery.repository.models.RpModelTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(context: Context) {

    private val dataStore = context.createDataStore("user_settings")

    private val keyTheme = preferencesKey<Int>("APP_THEME")

    // ----------------------------------------------------------------------------

    suspend fun getTheme(): Flow<RpModelTheme> {
        return dataStore
            .data
            .map { it[keyTheme] ?: 0 }
            .map { RpModelTheme.values()[it] }
    }

    suspend fun setTheme(theme: RpModelTheme): Flow<Boolean> {
        return flow {
            dataStore.edit { settings ->
                settings[keyTheme] = theme.ordinal
            }
            emit(true)
        }
    }
}