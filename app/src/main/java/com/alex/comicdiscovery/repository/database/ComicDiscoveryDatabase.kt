package com.alex.comicdiscovery.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex.comicdiscovery.repository.database.character.DbModelCharacter
import com.alex.comicdiscovery.repository.database.character.CharacterDao

@Database(entities = arrayOf(DbModelCharacter::class), version = 2)
abstract class ComicDiscoveryDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        lateinit var database: ComicDiscoveryDatabase

        fun init(context: Context) {
            database = Room
                .databaseBuilder(context, ComicDiscoveryDatabase::class.java, "comic-discovery-database.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}