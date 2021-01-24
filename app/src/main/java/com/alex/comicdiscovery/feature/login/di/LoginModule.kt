package com.alex.comicdiscovery.feature.login.di

import com.alex.comicdiscovery.feature.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}