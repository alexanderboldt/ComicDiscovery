package com.alex.repository

import com.alex.api.ApiClient
import com.alex.database.ComicDiscoveryDatabase
import com.alex.datastore.settings.SettingsDataStore
import com.alex.filemanager.FileManager
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object RepositoryModule {

    fun init() {
        loadKoinModules(
            module {
                // datasource classes
                single { ApiClient().routes }
                singleOf(::ComicDiscoveryDatabase)
                factoryOf(::SettingsDataStore)
                factoryOf(::FileManager)

                // repository classes
                factoryOf(::CharacterRepository)
                factoryOf(::ProfileRepository)
                factoryOf(::SearchRepository)
                factoryOf(::SettingsRepository)
                factoryOf(::StarlistRepository)
            }
        )
    }
}