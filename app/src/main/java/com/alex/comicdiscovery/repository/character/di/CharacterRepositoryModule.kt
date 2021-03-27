package com.alex.comicdiscovery.repository.character.di

import com.alex.comicdiscovery.repository.character.CharacterRepository
import org.koin.dsl.module

val characterRepositoryModule = module {
    factory { CharacterRepository(get(), get()) }
}