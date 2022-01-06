package com.alex.database

import android.content.Context
import com.alex.database.character.CharacterDao
import com.alex.database.starlist.StarlistDao
import com.alex.database.starlistCharacter.StarlistCharacterDao

object ComicDiscoveryDatabase {

    fun init(context: Context) {
        ComicDiscoveryDatabaseInternal.init(context)
    }

    // ----------------------------------------------------------------------------

    val starlistDao: StarlistDao
        get() = ComicDiscoveryDatabaseInternal.database.starlistDao()

    val characterDao: CharacterDao
        get() = ComicDiscoveryDatabaseInternal.database.characterDao()

    val starlistCharacterDao: StarlistCharacterDao
        get() = ComicDiscoveryDatabaseInternal.database.starlistCharacterDao()
}