package com.alex.repository.datasource.api.di

import com.alex.repository.datasource.api.ApiClient
import org.koin.dsl.module

val apiRoutesModule = module {
    single { ApiClient.routes }
}