package com.alex.comicdiscovery.feature.starlist.di

import com.alex.comicdiscovery.feature.starlist.StarlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val starlistModule = module {
    viewModel { StarlistViewModel(get()) }
}