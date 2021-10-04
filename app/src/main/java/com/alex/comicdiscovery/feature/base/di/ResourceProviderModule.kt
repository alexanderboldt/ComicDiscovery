package com.alex.comicdiscovery.feature.base.di

import com.alex.comicdiscovery.feature.base.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val resourceProviderModule = module {
    single { ResourceProvider(androidContext().resources) }
}