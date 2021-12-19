package com.alex.repository.datasource.datastore.settings.di

import com.alex.repository.datasource.datastore.settings.SettingsDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsDataStoreModule = module {
    single { SettingsDataStore(androidContext()) }
}