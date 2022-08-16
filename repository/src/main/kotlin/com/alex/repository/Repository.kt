package com.alex.repository

import com.alex.api.ApiClient
import com.alex.database.ComicDiscoveryDatabase
import com.alex.datastore.settings.SettingsDataStore
import com.alex.filemanager.FileManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object RepositoryModule {

    fun init() {
        loadKoinModules(
            module {
                // datasource classes
                factory { ApiClient.routes }
                single { ComicDiscoveryDatabase(androidContext()) }
                factory { SettingsDataStore(androidContext()) }
                factory { FileManager(androidContext()) }

                // repository classes
                factory { CharacterRepository(get(), get()) }
                factory { ProfileRepository(androidContext(), get()) }
                factory { SearchRepository(get()) }
                factory { SettingsRepository(get()) }
                factory { StarlistRepository(get(), get()) }
            }
        )
    }
}