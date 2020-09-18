package com.alex.comicdiscovery.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex.comicdiscovery.repository.database.character.Character
import com.alex.comicdiscovery.repository.database.character.CharacterDao

@Database(entities = arrayOf(Character::class), version = 1)
abstract class ComicDiscoveryDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        lateinit var database: ComicDiscoveryDatabase

        fun init(context: Context) {
            database = Room.databaseBuilder(context, ComicDiscoveryDatabase::class.java, "comic-discovery-database.db").build()
        }
    }
}