package com.alex.comicdiscovery.feature.character.overview.di

import com.alex.comicdiscovery.feature.character.overview.CharacterOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterOverviewModule = module {
    viewModel { CharacterOverviewViewModel(get(), get()) }
}