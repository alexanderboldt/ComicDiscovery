package com.alex.repository

import com.alex.api.ApiClient
import com.alex.database.ComicDiscoveryDatabase
import com.alex.datastore.profile.ProfileDataStore
import com.alex.datastore.settings.SettingsDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { ApiClient.routes }
    factory { ComicDiscoveryDatabase }
    factory { ProfileDataStore(androidContext()) }
    factory { SettingsDataStore(androidContext()) }

    factory { CharacterRepository(get(), get()) }
    factory { ProfileRepository(androidContext(), get()) }
    factory { SearchRepository(get()) }
    factory { SettingsRepository(get()) }
    factory { StarlistRepository(get(), get()) }
}