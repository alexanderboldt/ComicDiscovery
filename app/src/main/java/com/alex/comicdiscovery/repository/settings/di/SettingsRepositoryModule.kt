package com.alex.comicdiscovery.repository.settings.di

import com.alex.comicdiscovery.repository.settings.SettingsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsRepositoryModule = module {
    factory { SettingsRepository(androidApplication()) }
}