package com.alex.repository

import com.alex.api.ApiModule
import com.alex.database.DatabaseModule
import com.alex.datastore.settings.DatastoreModule
import com.alex.filemanager.FileManagerModule
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object RepositoryModule {

    fun init() {
        ApiModule.init()
        DatabaseModule.init()
        DatastoreModule.init()
        FileManagerModule.init()

        loadKoinModules(
            module {
                factoryOf(::CharacterRepository)
                factoryOf(::ProfileRepository)
                factoryOf(::SearchRepository)
                factoryOf(::SettingsRepository)
                factoryOf(::StarlistRepository)
            }
        )
    }
}