package com.alex.comicdiscovery.repository.datasource.datastore.profile.di

import com.alex.comicdiscovery.repository.datasource.datastore.profile.ProfileDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val profileDataStoreModule = module {
    factory { ProfileDataStore(androidContext()) }
}