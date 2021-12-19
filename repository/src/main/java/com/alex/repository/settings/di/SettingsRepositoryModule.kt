package com.alex.repository.settings.di

import com.alex.repository.settings.SettingsRepository
import org.koin.dsl.module

val settingsRepositoryModule = module {
    factory { SettingsRepository(get()) }
}