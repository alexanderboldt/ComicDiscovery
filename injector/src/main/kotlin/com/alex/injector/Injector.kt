package com.alex.injector

import android.content.Context
import com.alex.api.ApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val modulles = module {
    factory { ApiClient.routes }
}

class Injector {

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(modulles)
        }
    }
}