package com.example.comicdiscovery.feature.base.di

import com.example.comicdiscovery.feature.base.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val resourceProviderModule = module {
    single { ResourceProvider(androidContext()) }
}