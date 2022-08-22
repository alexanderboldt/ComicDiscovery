package com.alex.filemanager

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object FileManagerModule {

    fun init() {
        loadKoinModules(
            module {
                factoryOf(::FileManager)
            }
        )
    }
}