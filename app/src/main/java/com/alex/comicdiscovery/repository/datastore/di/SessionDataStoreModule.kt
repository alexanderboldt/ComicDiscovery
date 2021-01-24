package com.alex.comicdiscovery.repository.datastore.di

import com.alex.comicdiscovery.repository.datastore.SessionDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sessionDataStoreModule = module {
    single { SessionDataStore(androidApplication()) }
}