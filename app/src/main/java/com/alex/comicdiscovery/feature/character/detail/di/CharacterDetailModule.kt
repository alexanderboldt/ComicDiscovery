package com.alex.comicdiscovery.feature.character.detail.di

import com.alex.comicdiscovery.feature.character.detail.CharacterDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterDetailModule = module {
    viewModel { CharacterDetailViewModel(get(), get()) }
}