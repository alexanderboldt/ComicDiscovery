package com.alex.comicdiscovery.repository.datasource.database.di

import com.alex.comicdiscovery.repository.datasource.database.ComicDiscoveryDatabase
import org.koin.dsl.module

val comicDiscoveryDatabaseModule = module {
    single { ComicDiscoveryDatabase.database }
}