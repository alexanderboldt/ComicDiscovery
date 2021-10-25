package com.alex.comicdiscovery.feature.home.di

import com.alex.comicdiscovery.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
}