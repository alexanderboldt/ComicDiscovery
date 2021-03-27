package com.alex.comicdiscovery.repository.datasource.api.di

import com.alex.comicdiscovery.repository.datasource.api.ApiClient
import org.koin.dsl.module

val apiRoutesModule = module {
    single { ApiClient.routes }
}