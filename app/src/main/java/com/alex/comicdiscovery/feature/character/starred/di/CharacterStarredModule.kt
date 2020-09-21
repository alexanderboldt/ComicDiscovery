package com.alex.comicdiscovery.feature.character.starred.di

import com.alex.comicdiscovery.feature.character.starred.CharacterStarredViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterStarredModule = module {
    viewModel { CharacterStarredViewModel(get(), get()) }
}