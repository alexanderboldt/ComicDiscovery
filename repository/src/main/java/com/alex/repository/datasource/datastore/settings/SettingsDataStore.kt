package com.alex.repository.datasource.datastore.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.alex.repository.DsModelTheme
import com.alex.repository.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Settings> by dataStore("settings.proto", SettingsSerializer)

class SettingsDataStore(private val context: Context) {

    fun getTheme(): Flow<DsModelTheme> = context.settingsDataStore.data.map { it.theme }

    suspend fun setTheme(theme: DsModelTheme) {
        context.settingsDataStore.updateData {
            it.toBuilder().setTheme(theme).build()
        }
    }
}