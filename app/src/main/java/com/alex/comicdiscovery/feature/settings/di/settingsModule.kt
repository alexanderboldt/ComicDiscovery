package com.alex.comicdiscovery.feature.settings.di

import com.alex.comicdiscovery.feature.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel() }
}