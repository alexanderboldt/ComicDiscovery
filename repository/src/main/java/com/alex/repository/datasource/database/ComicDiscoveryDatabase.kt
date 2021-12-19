package com.alex.repository.datasource.database

import android.content.Context
import androidx.room.*
import com.alex.repository.datasource.database.character.DbModelCharacter
import com.alex.repository.datasource.database.character.CharacterDao
import com.alex.repository.datasource.database.converter.ListTypeConverter
import com.alex.repository.datasource.database.starlist.DbModelStarlist
import com.alex.repository.datasource.database.starlist.StarlistDao
import com.alex.repository.datasource.database.starlistCharacter.DbModelStarlistCharacter
import com.alex.repository.datasource.database.starlistCharacter.StarlistCharacterDao

@Database(entities = [DbModelCharacter::class, DbModelStarlist::class, DbModelStarlistCharacter::class], version = 4)
@TypeConverters(ListTypeConverter::class)
abstract class ComicDiscoveryDatabase : RoomDatabase() {

    abstract fun starlistDao(): StarlistDao
    abstract fun characterDao(): CharacterDao
    abstract fun starlistCharacterDao(): StarlistCharacterDao

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