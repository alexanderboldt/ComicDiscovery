package com.alex.comicdiscovery.repository.settings.di

import com.alex.comicdiscovery.repository.settings.SettingsRepository
import org.koin.dsl.module

val settingsRepositoryModule = module {
    factory { SettingsRepository(get()) }
}