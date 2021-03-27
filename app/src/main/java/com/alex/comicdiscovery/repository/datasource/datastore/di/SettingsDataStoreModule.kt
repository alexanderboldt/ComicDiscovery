package com.alex.comicdiscovery.repository.datasource.datastore.di

import com.alex.comicdiscovery.repository.datasource.datastore.SettingsDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsDataStoreModule = module {
    single { SettingsDataStore(androidApplication()) }
}