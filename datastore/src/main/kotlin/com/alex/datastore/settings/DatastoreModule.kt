package com.alex.datastore.settings

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object DatastoreModule {

    fun init() {
        loadKoinModules(
            module {
                factoryOf(::SettingsDataStore)
            }
        )
    }
}