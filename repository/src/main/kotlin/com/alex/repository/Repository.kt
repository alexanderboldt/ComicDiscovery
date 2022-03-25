package com.alex.repository

import android.content.Context
import com.alex.api.ApiClient
import com.alex.database.ComicDiscoveryDatabase
import com.alex.datastore.settings.SettingsDataStore
import com.alex.filemanager.FileManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Repository {

    fun init(context: Context) {
        ComicDiscoveryDatabase.init(context)

        loadKoinModules(
            module {
                // datasource-classes
                factory { ApiClient.routes }
                factory { ComicDiscoveryDatabase }
                factory { SettingsDataStore(androidContext()) }
                factory { FileManager(androidContext()) }

                // repository-classes
                factory { CharacterRepository(get(), get()) }
                factory { ProfileRepository(androidContext(), get()) }
                factory { SearchRepository(get()) }
                factory { SettingsRepository(get()) }
                factory { StarlistRepository(get(), get()) }
            }
        )
    }
}