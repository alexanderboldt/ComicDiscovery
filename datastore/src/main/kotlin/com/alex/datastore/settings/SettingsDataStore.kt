package com.alex.datastore.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.alex.datastore.Settings
import com.alex.datastore.settings.model.DsModelTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val Context.settingsDataStore: DataStore<Settings> by dataStore("settings.proto", SettingsSerializer)

class SettingsDataStore(private val context: Context) {

    fun getTheme(): Flow<DsModelTheme> = context
        .settingsDataStore
        .data
        .map { DsModelTheme.find(it.theme) }

    suspend fun setTheme(theme: DsModelTheme) {
        context.settingsDataStore.updateData {
            it.toBuilder().setTheme(theme.ordinal).build()
        }
    }
}