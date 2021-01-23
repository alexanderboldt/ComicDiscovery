package com.alex.comicdiscovery.repository.datastore.di

import com.alex.comicdiscovery.repository.datastore.SettingsDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsDataStoreModule = module {
    single { SettingsDataStore(androidApplication()) }
}