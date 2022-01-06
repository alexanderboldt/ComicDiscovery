package com.alex.database

import android.content.Context
import androidx.room.*
import com.alex.database.character.DbModelCharacter
import com.alex.database.character.CharacterDao
import com.alex.database.converter.ListTypeConverter
import com.alex.database.starlist.DbModelStarlist
import com.alex.database.starlist.StarlistDao
import com.alex.database.starlistCharacter.DbModelStarlistCharacter
import com.alex.database.starlistCharacter.StarlistCharacterDao

@Database(entities = [DbModelCharacter::class, DbModelStarlist::class, DbModelStarlistCharacter::class], version = 4)
@TypeConverters(ListTypeConverter::class)
internal abstract class ComicDiscoveryDatabaseInternal : RoomDatabase() {

    abstract fun starlistDao(): StarlistDao
    abstract fun characterDao(): CharacterDao
    abstract fun starlistCharacterDao(): StarlistCharacterDao

    companion object {
        lateinit var database: ComicDiscoveryDatabaseInternal

        fun init(context: Context) {
            database = Room
                .databaseBuilder(
                    context,
                    ComicDiscoveryDatabaseInternal::class.java,
                    "comic-discovery-database.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}