package com.alex.api.di

import com.alex.api.ApiClient
import org.koin.dsl.module

val apiRoutesModule = module {
    single { ApiClient.routes }
}