package com.alex.database

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object DatabaseModule {

    fun init() {
        loadKoinModules(
            module {
                singleOf(::ComicDiscoveryDatabase)
            }
        )
    }
}