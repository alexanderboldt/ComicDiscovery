package com.example.comicdiscovery.repository.character.di

import com.example.comicdiscovery.repository.character.CharacterRepository
import org.koin.dsl.module

val characterRepositoryModule = module {
    factory { CharacterRepository() }
}