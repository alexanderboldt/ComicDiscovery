package com.alex.comicdiscovery.repository.datasource.database

import android.content.Context
import androidx.room.*
import com.alex.comicdiscovery.repository.datasource.database.character.DbModelCharacter
import com.alex.comicdiscovery.repository.datasource.database.character.CharacterDao
import com.alex.comicdiscovery.repository.datasource.database.converter.ListTypeConverter

@Database(entities = [DbModelCharacter::class], version = 3)
@TypeConverters(ListTypeConverter::class)
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