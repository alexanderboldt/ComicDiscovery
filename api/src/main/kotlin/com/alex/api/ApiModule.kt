package com.alex.api

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ApiModule {

    fun init() {
        loadKoinModules(
            module {
                single { ApiClient().routes }
            }
        )
    }
}